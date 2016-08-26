import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Denis on 07.07.2016.
 * Open image from FILE_PATH, make it black and white, separate sylhouettes,
 * burn edge pixels off sylhouettes for separates matted silhouettes,
 * count off burn pixels regulated by ITERATE_COUNT,
 * removed object, that less than MIN_OBJECT_COEF of averages object size,
 * count remaining object and output it count.
 */
public class Silhouettes3 {
    /** PATH to image */
    private final static String FILE_PATH = "C:\\Users\\Denis\\IdeaProjects\\Silhouettes\\src\\2.jpg";
    /** Coef to remove object (remove, if less, then coef * average object size */
    private final static double MIN_OBJECT_COEF = 0.2;
    /* Qualifi pixel color - if average value of color channel less then colorSeparator - set black, else - white */
    private final static int COLOR_SEPARATOR = 126;
    /* Max possible links to pixel */
    private final static int MAX_LINKS_NUMBER = 8;
    /* Number of color channel - red, green, blue */
    private final static int COLOR_CHANEL_NUMBER = 3;
    /* Number of iterate burning edge sylhouettes pixels */
    private final static int ITERATE_COUNT = 4;

    public static void main(String args[]) {
        run();
    }

    private static void run() {
        byte[][] pictureArray = openImage(); //open imafe and make it black and white
        if (pictureArray != null) { //if image open, count sylhouettes
            byte background = getBackground(pictureArray); //get background value
            separatSylhouettesColors(pictureArray, background); //separate objects
            removeSilhouettesEdgePixels(pictureArray, background); //burn edge pixels of every object
            int silhouettesCount = filterImage(pictureArray, background); // remove small objects, get count of remaining objects
            System.out.println("There are " + silhouettesCount + " silhouettes on this image."); //output sylhouettes count
        }
    }

    /** Open image from FILE_PATH, call makeImageBlackAndWhite() to make it black and white and get int[][] of pixels color,
     * where 0 is means white and 1 - black pixel
     * @return int[][] pictureArray
     */
    private static byte[][] openImage() {
        byte[][] pictureArray = null;
        try {
            BufferedImage image = ImageIO.read(new File(FILE_PATH));
            pictureArray = makeImageBlackAndWhite(image);
        } catch (IOException e) {
            System.out.println("Can't open image");
            e.printStackTrace();
        }
        return pictureArray;
    }

    /** Get BufferedImage, get RGB of each image pixel in 2 threads, 1 treads work with left half of image,
     * 2 works with right half, get value of red, green and blue chanel of pixel,
     * if average value of all channels less then colorSeparator, write to int[][] pictureArray 1, that means
     * black color, else left 0, that means white color
     * @param image
     * @return int[][] pictureArray
     */
    private static byte[][] makeImageBlackAndWhite(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] pictureArray = new byte[width][height];
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int x = 0; x < width / 2; x++) {
                    for (int y = 0; y < height; y++) {
                        makePixelBlackOrWhite(x, y, image, pictureArray);
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int x = width / 2; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        makePixelBlackOrWhite(x, y, image, pictureArray);
                    }
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pictureArray;
    }
    /*
    Get Pixel RGB, if it less then COLOR_SEPARATOR value, set pixel in pixelArray as black
    @param x - x coordinate of pixel
    @param y - y coordinate of pixel
    @param image - BufferedImage
    @param pictureArray - array of black and white values pf pixels
     */
    private static void makePixelBlackOrWhite(int x, int y, BufferedImage image, byte[][] pictureArray) {
        byte black = 1;
        int pixelRGB = image.getRGB(x, y);
        int blue = pixelRGB & 0xff;
        int green = (pixelRGB & 0xff00) >> 8;
        int red = (pixelRGB & 0xff0000) >> 16;
        int averageColor = (blue + green + red) / COLOR_CHANEL_NUMBER;
        if (averageColor <= COLOR_SEPARATOR) {
            pictureArray[x][y] = black;
            //System.out.println("+1");
        }
    }

    /** Run on borders image (int[][] pictureArray, that means image),
     * count white and black pixels and return 0 (white) if white pixels more that black, or 1 (black), if else
     * @param pictureArray
     * @return int backgroundColor
     */
    private static byte getBackground(byte[][] pictureArray) {
        int width = pictureArray.length;
        int height = pictureArray[0].length;
        int pixelsColor = 0;
        for (int x = 0; x < width; x++) {
            int y = 0;
            pixelsColor = (pictureArray[x][y] == 1) ? pixelsColor + 1 : pixelsColor - 1;
            y = height - 1;
            pixelsColor = (pictureArray[x][y] == 1) ? pixelsColor + 1 : pixelsColor - 1;
        }
        for (int y = 0; y < height; y++) {
            int x = 0;
            pixelsColor = (pictureArray[x][y] == 1) ? pixelsColor + 1 : pixelsColor - 1;
            x = width - 1;
            pixelsColor = (pictureArray[x][y] == 1) ? pixelsColor + 1 : pixelsColor - 1;
        }
        byte backgroundColor;
        if(pixelsColor <= 0) {
            backgroundColor = 0;
        }
        else {
            backgroundColor = 1;
        }
        return backgroundColor;
    }

    /** Work with HashMap<Integer, Integer> colorPixels, get int picture array and coordinates in array,
     * if value on this coordinates is in HashMap as key, increment value on this key, else  add key with value 1
     * @param colorPixels
     * @param pictureArray
     * @param x
     * @param y
     */
    private static void countColorPixels(MyHashMap<Byte, Integer> colorPixels, byte[][] pictureArray, int x, int y) {
        if (!colorPixels.containsKey(pictureArray[x][y])) {
            colorPixels.put(pictureArray[x][y], 1);
        } else {
            int pixelsCount = colorPixels.get(pictureArray[x][y]) + 1;
            colorPixels.put(pictureArray[x][y], pixelsCount);
        }
    }

    /** Run on int[][] pictureArray, when found pixel, which color != background color, call sylhoetteSetNewColor,
     * which found sylhouette with bfs and set color of each pixel of sylhouette to sylhoette color value
     * (new color for each sylhouette)
     * @param pictureArray
     * @param background
     */
    private static void separatSylhouettesColors(byte[][] pictureArray, byte background) {
        int width = pictureArray.length;
        int height = pictureArray[0].length;
        boolean[][] iWasThere = new boolean[width][height];
        byte newColor = 2;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                byte pixelColor = pictureArray[x][y];
                if (pixelColor != background && pixelColor < 2) {
                    sylhoetteSetNewColor(pictureArray, pixelColor, newColor, x, y, iWasThere);
                    newColor++;
                }
            }
        }
    }

    /** BFS search sylhoette, sset color of each pixel of sylhouette to sylhoette color value
     * (new color for each sylhouette)
     * @param pictureArray
     * @param pixelColor
     * @param newColor
     * @param x
     * @param y
     * @param iWasThere
     */
    private static void sylhoetteSetNewColor(byte[][] pictureArray, byte pixelColor, byte newColor, int x, int y, boolean[][] iWasThere) {
        int width = pictureArray.length;
        int height = pictureArray[0].length;
        int[] pixelCoordinates = {x, y};
        MyDeque<int[]> coordinatesDeque = new MyDeque<>();
        coordinatesDeque.add(pixelCoordinates);
        while (!coordinatesDeque.isEmpty()) {
            //System.out.println(coordinatesDeque.size());
            pixelCoordinates = coordinatesDeque.poll();
            x = pixelCoordinates[0];
            y = pixelCoordinates[1];

            if (pictureArray[x][y] != newColor) {
                pictureArray[x][y] = newColor;
                iWasThere[x][y] = true;
                if ((x - 1) > 0) {
                    if (pixelColor == pictureArray[x - 1][y]) {
                        int[] coordinates = {(x - 1), y};
                        addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                    }
                    if ((y + 1) < height && pixelColor == pictureArray[x - 1][y + 1]) {
                        int[] coordinates = {(x - 1), (y + 1)};
                        addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                    }
                    if ((y - 1) > 0 && pixelColor == pictureArray[x - 1][y - 1]) {
                        int[] coordinates = {(x - 1), (y - 1)};
                        addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                    }
                }
                if ((x + 1) < width) {
                    if ((y + 1) < height && pixelColor == pictureArray[x + 1][y + 1]) {
                        int[] coordinates = {(x + 1), (y + 1)};
                        addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                    }
                    if (pixelColor == pictureArray[x + 1][y]) {
                        int[] coordinates = {(x + 1), y};
                        addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                    }
                    if ((y - 1) > 0 && pixelColor == pictureArray[x + 1][y - 1]) {
                        int[] coordinates = {(x + 1), (y - 1)};
                        addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                    }

                }
                if ((y + 1) < height && pixelColor == pictureArray[x][y + 1]) {
                    int[] coordinates = {x, (y + 1)};
                    addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                }
                if ((y - 1) > 0 && pixelColor == pictureArray[x][y - 1]) {
                    int[] coordinates = {x, (y - 1)};
                    addCoordinatesToDeque(coordinates, coordinatesDeque, iWasThere);
                }
            }
        }
    }

    /** If coordinates aren't in deque, add them to it
     * @param coordinates
     * @param coordinatesDeque
     * @param iWasThere
     */
    private static void addCoordinatesToDeque(int[] coordinates, MyDeque<int[]> coordinatesDeque, boolean[][] iWasThere) {
        int x = coordinates[0];
        int y = coordinates[1];
        if (!iWasThere[x][y]) {
            iWasThere[x][y] = true;
            //System.out.println(x + " " + y);
            coordinatesDeque.add(coordinates);
        }
    }

    /** Find border pixels of sylhoettes, add it to pixelsBurn, set up background color for coordinates in array,
     * repeat ITERATE_COUNT times
     * @param pictureArray
     * @param background
     */
    private static void removeSilhouettesEdgePixels(byte[][] pictureArray, byte background) {
        int width = pictureArray.length;
        int height = pictureArray[0].length;
        MyArrayList<int[]> pixelsBurn = new MyArrayList<>();
        for (int i = 0; i < ITERATE_COUNT; i++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (pictureArray[x][y] != background) {
                        int linksNumber = getNumberLinks(pictureArray, x, y);
                        if (linksNumber < MAX_LINKS_NUMBER) {
                            int[] coordinates = {x, y};
                            pixelsBurn.add(coordinates);
                        }
                    }
                }
            }
            for (int[] coordinates : pixelsBurn) {
                pictureArray[coordinates[0]][coordinates[1]] = background;
            }
        }
    }

    /** Count sylhouettes and pixels therein
     * @param pictureArray
     * @param backgroundRGB
     * @return HashMap<Integer, Integer> colorPixels - keys - sylhouettes, value - pixels therein
     */
    private static MyHashMap<Byte, Integer> silhouettesCount(byte[][] pictureArray, byte backgroundRGB) {
        MyHashMap<Byte, Integer> colorPixels = new MyHashMap<>();
        for (int x = 0; x < pictureArray.length; x++) {
            for (int y = 0; y < pictureArray[0].length; y++) {
                if (pictureArray[x][y] != backgroundRGB) {
                    countColorPixels(colorPixels, pictureArray, x, y);
                }
            }
        }
        return colorPixels;
    }
    /* Count sum of all pixels in sylhouettes
       @return int silhouettesPixelsCount
     */
    private static int countAllSilhouettesPixels(MyHashMap<Byte, Integer> colorPixels) {
        int silhouettesPixelsCount = 0;
        for (MyHashMap.Entry entry : colorPixels) {
            silhouettesPixelsCount += (int)entry.getValue();
        }
        return silhouettesPixelsCount;
    }

    /** Count number of links to pixel - find neighbors with same color and return it number
     * @param pictureArray
     * @param x
     * @param y
     * @return int numberLinks
     */
    private static int getNumberLinks(byte[][] pictureArray, int x, int y) {
        byte numberLinks = 0;
        int width = pictureArray.length;
        int height = pictureArray[0].length;
        byte pixelRGB = pictureArray[x][y];
        if ((x - 1) > 0) {
            if (pixelRGB == pictureArray[x - 1][y]) {
                numberLinks++;
            }
            if ((y + 1) < height && pixelRGB == pictureArray[x - 1][y + 1]) {
                numberLinks++;
            }
            if ((y - 1) > 0 && pixelRGB == pictureArray[x - 1][y - 1]) {
                numberLinks++;
            }
        }
        if ((x + 1) < width) {
            if (pixelRGB == pictureArray[x + 1][y]) {
                numberLinks++;
            }
            if ((y + 1) < height && pixelRGB == pictureArray[x + 1][y + 1]) {
                numberLinks++;
            }
            if ((y - 1) > 0 && pixelRGB == pictureArray[x + 1][y - 1]) {
                numberLinks++;
            }
        }
        if ((y + 1) < height && pixelRGB == pictureArray[x][y + 1]) {
            numberLinks++;
        }
        if ((y - 1) > 0 && pixelRGB == pictureArray[x][y - 1]) {
            numberLinks++;
        }

        return numberLinks;
    }
    /* Call silhouettesCount() to get count of sylhouettes and count pixels in them,
        call countAllSilhouettesPixels() and calculate averageSilhouettePixels,
        remove objects, less then averageSilhouettePixels * MIN_OBJECT_COEF
        @return int count of sylhouettes
     */
    private static int filterImage(byte[][] pictureArray, byte backgroundRGB) {
        MyHashMap<Byte, Integer> colorPixels = silhouettesCount(pictureArray, backgroundRGB);
        int silhouettesCount = colorPixels.size();
        if (silhouettesCount < 1) {
            System.out.println("There are no sylhouettes on picture or too much pixels burn, try to change break links settings");
            return silhouettesCount;
        }
        int silhouettesPixelsCount = countAllSilhouettesPixels(colorPixels);
        double averageSilhouettePixels = silhouettesPixelsCount / silhouettesCount;
        double minSilhouettePixels = averageSilhouettePixels * MIN_OBJECT_COEF;
        MyArrayList<Byte> colorSilhouettesRemove = new MyArrayList<>();
        for (MyHashMap.Entry<Byte, Integer> entry : colorPixels) {
            if (entry.getValue() < minSilhouettePixels) {
                colorSilhouettesRemove.add(entry.getKey());
            }
        }
        for (Byte entry : colorSilhouettesRemove) {
            colorPixels.remove(entry);
        }
        return colorPixels.size();
    }

}
