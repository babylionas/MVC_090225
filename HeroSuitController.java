package MVC;

import java.util.*;

public class HeroSuitController {
    private List<HeroSuit> suits;
    private HeroSuitGUI view;
    private Map<String, Integer> repairCount;

    public HeroSuitController(List<HeroSuit> suits, HeroSuitGUI view) {
        this.suits = suits;
        this.view = view;
        this.repairCount = new HashMap<>();
    }

    // ฟังก์ชั่นเริ่มต้นในการทำงาน
    public void start() {
        while (true) {
            String id = view.getInputId();  // รับ ID จาก View
            HeroSuit suit = findSuitById(id);

            if (suit == null) {
                view.showInvalidMessage("No set found in the system!");
                continue;
            }

            view.showSuitStatus(suit);

            if (!suit.isValid()) {
                if (view.askForRepair()) {
                    suit.repair();
                    repairCount.put(suit.getType(), repairCount.getOrDefault(suit.getType(), 0) + 1);
                    view.showRepairSuccess();
                    view.showSuitStatus(suit);
                }
            }
        }
    }

    // ค้นหาชุดซุปเปอร์ฮีโร่ตามรหัส
    public HeroSuit findSuitById(String id) {
        for (HeroSuit suit : suits) {
            if (suit.getId().equals(id)) {
                return suit;
            }
        }
        return null;
    }

    // ฟังก์ชันดึงสรุปการซ่อม
    public String getRepairSummary() {
        StringBuilder summary = new StringBuilder();
        repairCount.forEach((type, count) -> summary.append(type).append(": ").append(count).append(" repairs\n"));
        return summary.toString();
    }

    // ฟังก์ชันซ่อมชุด
    public void repairSuit(String id) {
        HeroSuit suit = findSuitById(id);
        if (suit != null && !HeroSuitValidator.isValidSuit(suit)) {
            suit.repair();  // สมมติว่าการซ่อมเพิ่มทนทาน
            repairCount.put(suit.getType(), repairCount.getOrDefault(suit.getType(), 0) + 1);
            view.showRepairSuccess();  // แสดงข้อความเมื่อซ่อมสำเร็จ
            // แสดงข้อมูลการซ่อมแซม
            view.updateRepairSummary(repairCount);
        }
    }
}
