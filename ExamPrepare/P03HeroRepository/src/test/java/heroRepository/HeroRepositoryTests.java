package heroRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HeroRepositoryTests {
    HeroRepository heroRepository;
    Hero hero1;

    @Before
    public void setup() {
        heroRepository = new HeroRepository();
        hero1 = new Hero("Pesho", 50);
    }

    @Test
    public void testGetCount() {
        heroRepository.create(hero1);
        Assert.assertEquals(1, heroRepository.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithHeroNull() {
        hero1 = null;
        heroRepository.create(hero1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithExistsHero() {
        heroRepository.create(hero1);
        heroRepository.create(hero1);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveWithHeroNullName() {
        Hero hero2 = new Hero(null, 30);
        heroRepository.create(hero2);
        heroRepository.remove(hero2.getName());
    }

    @Test
    public void testRemoveWithHeroSuccsess() {
        heroRepository.create(hero1);
        boolean remove = heroRepository.remove(hero1.getName());
        Assert.assertTrue(remove);
    }

    @Test
    public void testGetHero() {
        heroRepository.create(hero1);
        Hero hero = heroRepository.getHero("Pesho");
        Assert.assertEquals(hero.getName(), hero1.getName());
    }

    @Test
    public void testGetHeroWithHigthestLevel() {
        Hero hero2 = new Hero("Ivan", 60);
        Hero hero3 = new Hero("Martin", 100);
        heroRepository.create(hero1);
        heroRepository.create(hero2);
        heroRepository.create(hero3);
        Assert.assertEquals(hero3, heroRepository.getHeroWithHighestLevel());
    }

    @Test
    public void testGetHeroesCollections() {
        Hero hero2 = new Hero("Ivan", 60);
        Hero hero3 = new Hero("Martin", 100);
        heroRepository.create(hero1);
        heroRepository.create(hero2);
        heroRepository.create(hero3);
        Collection<Hero> heroes = heroRepository.getHeroes();
        Assert.assertEquals(heroes.size(), heroRepository.getCount());
    }
}