import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class test {
    private ISearcher searcher = new ClassSearcher();

    private final int MAXSIZE = 100000;
    private String[] classes;
    private long[] dates;

    @Test
    public void testCorrectness(){
        String[] classes = {
                "bournemouth", "burnley", "barcelona", "betis",
                "bastia", "cska", "zenit", "borussia d",
                "bayern", "juventus", "brugge", "benfica",
                "braga", "borussia m", "bayer", "bordeaux"};

        long[] dates = {
                82l, 23l, 18l, 171l,
                16l, 8l, 16l, 76l,
                182l, 10l, 23l, 18l,
                16l, 172l, 98l, 10l};

        String[] expected = {
                "bayern", "borussia m", "betis", "bayer",
                "bournemouth", "borussia d",  "brugge", "burnley",
                "barcelona", "benfica", "bastia", "braga"};

        searcher.refresh(classes, dates);
        String[] actual = searcher.guess("b");
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testNotFoundElement(){
        String[] classes = {
                "bournemouth", "burnley", "barcelona", "betis",
                "bastia", "cska", "zenit", "borussia d",
                "bayern", "juventus", "brugge", "benfica",
                "braga", "borussia m", "bayer", "bordeaux"};

        long[] dates = {
                82l, 23l, 18l, 171l,
                16l, 8l, 16l, 76l,
                182l, 10l, 23l, 18l,
                16l, 172l, 98l, 10l};

        String[] expected = new String[0];
        searcher.refresh(classes, dates);
        String[] actual = searcher.guess("ural");
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testAverageGuessSpeed(){
        classes = classNamesCreator();
        dates = datesCreator();
        searcher.refresh(classes, dates);
        String start;
        long guessTime = 0;

        for(int i = 0; i < 50; i++){
            start = stringCreator();
            long startTime = System.currentTimeMillis();
            searcher.guess(start);
            long endTime = System.currentTimeMillis();
            guessTime += endTime - startTime;
        }
        Assert.assertTrue(guessTime / 50 < 300);
    }

    @Test
    public void testAverageRefreshSpeed(){
        long desiredTime = 2000;
        long refreshTime = 0;
        for(int i = 0; i < 50; i++) {
            classes = classNamesCreator();
            dates = datesCreator();
            long startTime = System.currentTimeMillis();
            searcher.refresh(classes, dates);
            long endTime = System.currentTimeMillis();
            refreshTime += endTime - startTime;
        }
        Assert.assertTrue(refreshTime / 50 < desiredTime);

    }

    public String[] classNamesCreator(){
        String[] classes = new String[MAXSIZE];
        for(int i = 0; i < MAXSIZE; i++){
            char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPRSTUVWXYZ0123456789".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            int length = random.nextInt(31) + 1;
            for(int j = 0; j < length; j++){
                char nextLetter = chars[random.nextInt(chars.length)];
                sb.append(nextLetter);
            }
            classes[i] = sb.toString();
        }
        return classes;
    }

    public long[] datesCreator(){
        long[] dates = new long[MAXSIZE];
        for(int i = 0; i < MAXSIZE; i++) {
            Random random = new Random();
            long num = random.nextLong();
            if(num < 0){
                num = Math.abs(num);
            }
            dates[i] = num;
        }
        return dates;
    }

    public String stringCreator(){
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = random.nextInt(31) + 1;
        for(int j = 0; j < length; j++){
            char nextLetter = chars[random.nextInt(chars.length)];
            sb.append(nextLetter);
        }
        return sb.toString();
    }
}
