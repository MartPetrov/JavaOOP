package gifts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class GiftFactoryTests {
    private GiftFactory giftFactory;

    @Before
    public void setUp() {
    this.giftFactory = new GiftFactory();
    }

    @Test
    public void createGiftValid() {
        Gift gift1 =new Gift("gift1", 2.00);
        List<Gift> giftList = new ArrayList<>();
        giftList.add(gift1);
        this.giftFactory.createGift(gift1);
        int count = this.giftFactory.getCount();
        Assert.assertEquals(count,1);
        Assert.assertEquals(giftList.get(0),giftFactory.getPresent("gift1"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void createGiftInValid() {
        Gift gift1 =new Gift("gift1", 2.00);
        giftFactory.createGift(gift1);
        giftFactory.createGift(gift1);
    }


    @Test
    public void removeGiftValid() {
        Gift gift1 =new Gift("gift1", 2.00);
        this.giftFactory.removeGift("gift1");
        Assert.assertEquals(giftFactory.getCount(),0);
    }
    @Test(expected = NullPointerException.class)
    public void removeGiftInvalid() {
        Gift gift1 =new Gift("gift1", 2.00);
        this.giftFactory.removeGift(null);
    }

    @Test
    public void getPresentWithLeastMagic() {
        Gift gift1 =new Gift("gift1", 2.00);
        Gift gift2 =new Gift("gift2", 5.00);
        Gift gift3 =new Gift("gift3", 66.00);
        List<Gift> giftList = new ArrayList<>();
        giftList.add(gift1);
        giftList.add(gift2);
        giftList.add(gift3);
        giftList.sort(Comparator.comparing(Gift::getMagic));
        for (Gift gift: giftList) {
            this.giftFactory.createGift(gift);
        }
        Gift result = this.giftFactory.getPresentWithLeastMagic();
        Assert.assertEquals(giftList.get(0),result);
    }

    @Test
    public void getPresent() {
        Gift gift1 =new Gift("gift1", 2.00);
        Gift gift2 =new Gift("gift2", 5.00);
        Gift gift3 =new Gift("gift3", 66.00);
        List<Gift> giftList = new ArrayList<>();
        giftList.add(gift1);
        giftList.add(gift2);
        giftList.add(gift3);
        for (Gift gift: giftList) {
            this.giftFactory.createGift(gift);
        }
        Collection<Gift> giftCollection = this.giftFactory.getPresents();
        List<Gift> result = new ArrayList<>(giftCollection);
        Assert.assertEquals(giftList,result);
    }

}
