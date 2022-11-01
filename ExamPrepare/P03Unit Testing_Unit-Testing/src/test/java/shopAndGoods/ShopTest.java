package shopAndGoods;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.Map;

public class ShopTest {

    private Shop shop;
    private Goods goods;
    private Goods goods2;
    private Goods goods3;

    @Before
    public void setUp() {
        shop = new Shop();
        goods = new Goods("Shelves1", "12345");
        goods2 = new Goods("Shelves2", "1234545");
        goods3 = new Goods("Shelves3", "542312");
    }

    @Test
    public void testMakeShopAndTakeShelves() {
        Map<String, Goods> result = shop.getShelves();
        Assert.assertEquals(12, result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsInShopInvalidShelf() throws OperationNotSupportedException {
        Goods goodsInvalid = new Goods("Drink", "1234545");
        shop.addGoods("Drink", goodsInvalid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsInShopNullShelft() throws OperationNotSupportedException {
        Map<String, Goods> result = shop.getShelves();
        Goods goodsInvalid = new Goods("Drink", "1234545");
        shop.addGoods("Shelves1", goodsInvalid);
        shop.addGoods("Shelves1", goodsInvalid);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testAddGoodsInShopItemExist() throws OperationNotSupportedException {
        Map<String, Goods> result = shop.getShelves();
        Goods goodsInvalid = new Goods("Drink", "1234545");
        shop.addGoods("Shelves1", goodsInvalid);
        shop.addGoods("Shelves2", goodsInvalid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsInShopInvalidShelf() throws OperationNotSupportedException {
        Goods goods = new Goods("Drink", "1234545");
        shop.removeGoods("Drink", goods);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsInShopWithNotValidGoods() throws OperationNotSupportedException {
        Goods goods = new Goods("Bread", "1234545");
        Goods goods2 = new Goods("Drink", "1234545");
        shop.addGoods("Shelves1", goods);
        shop.removeGoods("Shelves1", goods2);
    }

    @Test
    public void testRemoveGoodsInShopValidRemove() throws OperationNotSupportedException {
        Goods goods = new Goods("Bread", "1234545");
        shop.addGoods("Shelves1", goods);
        String removeInfo = shop.removeGoods("Shelves1", goods);
        Map<String, Goods> shelves = shop.getShelves();
        Assert.assertNull(shelves.get("Shelves1"));
        Assert.assertEquals(removeInfo, String.format("Goods: %s is removed successfully!", goods.getGoodsCode()));
    }


}