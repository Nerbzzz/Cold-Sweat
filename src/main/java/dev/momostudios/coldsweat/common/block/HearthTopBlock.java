package dev.momostudios.coldsweat.common.block;

import dev.momostudios.coldsweat.core.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.momostudios.coldsweat.core.init.BlockInit;

import java.util.*;

public class HearthTopBlock extends Block
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final Map<Direction, VoxelShape> SHAPES = new HashMap<>();

    public static Properties getProperties()
    {
        return Properties
                .of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2f)
                .explosionResistance(10f)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .dynamicShape();
    }

    public HearthTopBlock()
    {
        super(HearthTopBlock.getProperties());
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
        runCalculation(Shapes.or(
            Block.box(3, -15, 4, 13, 3, 12), // Shell
            Block.box(8, 3, 6, 12, 15, 10), // Exhaust 1
            Block.box(6, 11.5, 5.5, 8, 15.5, 10.5), // Exhaust 2
            Block.box(13, -11, 6, 16, -3, 10), // Water Canister
            Block.box(0, -11, 6, 3, -3, 10))); // Lava Canister)
    }

    static void calculateShapes(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

        int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1],
                Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        SHAPES.put(to, buffer[0]);
    }

    static void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            calculateShapes(direction, shape);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        return SHAPES.get(state.getValue(FACING));
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult)
    {
        if (!worldIn.isClientSide && worldIn.getBlockState(pos.below()).getBlock() instanceof HearthBlock hearthBlock)
        {
            hearthBlock.use(worldIn.getBlockState(pos.below()), worldIn, pos.below(), player, hand, rayTraceResult);
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        if (level.getBlockState(pos.below()).getBlock() != BlockInit.HEARTH.get())
        {
            this.destroy(level, pos, state);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            if (level.getBlockState(pos.below()).getBlock() == BlockInit.HEARTH.get())
            {
                level.destroyBlock(pos.below(), false);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos, BlockState state) {
        return new ItemStack(ItemInit.HEARTH.get());
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(ItemInit.HEARTH.get(), 1));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation direction)
    {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48855_) {
        p_48855_.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}