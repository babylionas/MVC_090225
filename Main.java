package MVC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // อ่านข้อมูลชุดซุปเปอร์ฮีโร่จากไฟล์ CSV
        List<HeroSuit> suits = readSuitsFromCSV("hero_suits.csv");

        // สร้างและแสดงผล HeroSuitGUI ที่เชื่อมต่อกับ HeroSuitController
        HeroSuitGUI gui = new HeroSuitGUI(suits);
    }

    public static List<HeroSuit> readSuitsFromCSV(String filePath) {
        List<HeroSuit> suits = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // ข้าม header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0].trim();
                String type = values[1].trim();
                int durability = Integer.parseInt(values[2].trim());

                // สร้าง HeroSuit และเพิ่มใน List
                HeroSuit suit = new HeroSuit(id, type, durability);
                suits.add(suit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return suits;
    }
}
