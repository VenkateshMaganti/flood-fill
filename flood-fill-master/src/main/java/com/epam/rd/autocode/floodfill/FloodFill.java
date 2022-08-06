package com.epam.rd.autocode.floodfill;

import java.util.HashMap;
import java.util.Map;

public interface FloodFill {
    void flood(final String map, final FloodLogger logger);

    static FloodFill getInstance() {
        //throw new UnsupportedOperationException();
        return new FloodFill() {

            @Override
            public void flood(String map, FloodLogger logger) {

                /*
                 * Print the rectangle region provided
                 */
                logger.log(map);

                /*
                 * Convert the map string to an char 2D array for easy convenience.
                 */
                String[] area = map.split("\n");
                char[][] rectangle = new char[area.length][];
                for (int i = 0; i < area.length; i++) {
                    rectangle[i] = area[i].toCharArray();
                }

                /*
                 * Check all the rectangle region is flooded
                 */
                int rowFilled = 0;
                for (int i = 0; i < rectangle.length; i++) {
                    int water = 0;
                    for (int j = 0; j < rectangle[i].length; j++) {
                        if (rectangle[i][j] == FloodFill.WATER) {
                            water++;
                        }
                    }
                    if (water == rectangle[i].length) {
                        rowFilled++;
                    }
                }
                if (rowFilled == area.length) {
                    return;
                } else {
                    /*
                     * Capture the regions to flood in the rectangle.
                     */
                    Map<Integer, String> positionsToFill = new HashMap<Integer, String>();
                    int index = 0;
                    for (int i = 0; i < rectangle.length; i++) {
                        for (int j = 0; j < rectangle[i].length; j++) {
                            if (rectangle[i][j] == FloodFill.WATER) {
                                // fill left
                                if ((j - 1) >= 0) {
                                    positionsToFill.put(index++, (i + "," + (j - 1)));
                                }
                                // fill right
                                if ((j + 1) < (rectangle[i].length)) {
                                    positionsToFill.put(index++, (i + "," + (j + 1)));
                                }
                                // fill up
                                if ((i - 1) >= 0) {
                                    positionsToFill.put(index++, ((i - 1) + "," + j));
                                }
                                // fill bottom
                                if ((i + 1) < (rectangle.length)) {
                                    positionsToFill.put(index++, ((i + 1) + "," + j));
                                }
                            }
                        }
                    }
                    /*
                     * Flood one more level in the rectangle region
                     */
                    for (Map.Entry<Integer, String> each : positionsToFill.entrySet()) {
                        String[] indexes = each.getValue().split(",");
                        int i = Integer.parseInt(indexes[0]);
                        int j = Integer.parseInt(indexes[1]);
                        rectangle[i][j] = FloodFill.WATER;
                    }
                    /*
                     * Convert back the 2d array to string
                     */
                    String tmp = "";
                    for (int i = 0; i < rectangle.length; i++) {
                        for (int j = 0; j < rectangle[i].length; j++) {
                            tmp += rectangle[i][j];
                        }
                        tmp += "\n";
                    }
                    tmp = tmp.substring(0, tmp.length() - 1);
                    map = tmp;
                    this.flood(map, logger);
                }
            }
        };
    }

    char LAND = '█';
    char WATER = '░';
}
