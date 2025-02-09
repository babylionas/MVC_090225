package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroSuitGUI extends JFrame {
    private JTextField txtSuitId;
    private JLabel lblSuitInfo;
    private JButton btnCheck, btnRepair;
    private JTextArea txtRepairHistory, txtCheckHistory, txtRepairSummary;
    private HeroSuitController controller;

    private Map<String, Integer> repairCount = new HashMap<>();

    public HeroSuitGUI(List<HeroSuit> suits) {
        // สร้าง HeroSuitController และส่ง List ของชุดซุปเปอร์ฮีโร่
        controller = new HeroSuitController(suits, this);
    
        setTitle("Superhero Suit Screening System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        Font font = new Font("Tahoma", Font.PLAIN, 14);
    
        // ---------------- Input Panel ----------------
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblInput = new JLabel("SUIT_ID: ");
        lblInput.setFont(font);
        inputPanel.add(lblInput);
        txtSuitId = new JTextField(10);
        txtSuitId.setFont(font);
        inputPanel.add(txtSuitId);

        // เพิ่มข้อความแนะนำ
        JLabel lblSuitIdFormat = new JLabel("Must be a 6-digit");
        lblSuitIdFormat.setFont(font);
        inputPanel.add(lblSuitIdFormat);

        btnCheck = new JButton("Check");
        btnCheck.setFont(font);
        inputPanel.add(btnCheck);
        add(inputPanel);
    
        // ---------------- Repair Button Panel ----------------
        JPanel repairButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRepair = new JButton("Repair");
        btnRepair.setFont(font);
        btnRepair.setVisible(false);
        repairButtonPanel.add(btnRepair);
        add(repairButtonPanel);
    
        // ---------------- Suit Info Panel ----------------
        JPanel infoPanel = new JPanel();
        lblSuitInfo = new JLabel("The data set will be displayed here.", SwingConstants.CENTER);
        lblSuitInfo.setFont(font);
        lblSuitInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(lblSuitInfo);
        add(infoPanel);
    
        // ---------------- Check History Panel ----------------
        JLabel lblCheckHistory = new JLabel("History of Check:");
        lblCheckHistory.setFont(font);
        lblCheckHistory.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(lblCheckHistory);
        
        txtCheckHistory = new JTextArea(5, 40);
        txtCheckHistory.setFont(font);
        txtCheckHistory.setEditable(false);
        JScrollPane checkScrollPane = new JScrollPane(txtCheckHistory);
        add(checkScrollPane);
    
        // ---------------- Repair History Panel ---------------- 
        JLabel lblRepairHistory = new JLabel("Repair History:");
        lblRepairHistory.setFont(font);
        lblRepairHistory.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(lblRepairHistory);
        
        txtRepairHistory = new JTextArea(5, 40);
        txtRepairHistory.setFont(font);
        txtRepairHistory.setEditable(false);
        JScrollPane repairScrollPane = new JScrollPane(txtRepairHistory);
        add(repairScrollPane);
    
        // ---------------- Repair Summary Panel ----------------
        JLabel lblRepairSummary = new JLabel("Repair Summary:");
        lblRepairSummary.setFont(font);
        lblRepairSummary.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblRepairSummary);
        
        txtRepairSummary = new JTextArea(5, 40);
        txtRepairSummary.setFont(font);
        txtRepairSummary.setEditable(false);
        JScrollPane summaryScrollPane = new JScrollPane(txtRepairSummary);
        add(summaryScrollPane);
    
        // สร้างค่าเริ่มต้นให้กับ Repair Summary
        initializeRepairSummary();
    
        // ---------------- Event Handling ----------------
        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSuit();
            }
        });
    
        btnRepair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repairSuit();
            }
        });
    
        pack();
        setLocationRelativeTo(null); // จัดให้อยู่ตรงกลางหน้าจอ
        setVisible(true);
    }
    
    private void initializeRepairSummary() {
        // สร้างค่าเริ่มต้นให้กับประเภทชุดทั้งหมด
        Map<String, Integer> defaultRepairCount = Map.of(
            "ชุดปกปิดตัวตน", 0,
            "ชุดลอบเร้น", 0,
            "ชุดทรงพลัง", 0
        );
        updateRepairSummary(defaultRepairCount);
    }
    
    // ฟังก์ชันที่ใช้เพื่อแสดงข้อมูลจำนวนชุดที่ซ่อมแซมในแต่ละประเภท
    public void updateRepairSummary(Map<String, Integer> repairCount) {
        StringBuilder summary = new StringBuilder("Repair Summary:\n");
        for (Map.Entry<String, Integer> entry : repairCount.entrySet()) {
            summary.append(entry.getKey())
                   .append(": ")
                   .append(entry.getValue())
                   .append(" repairs\n");
        }
        txtRepairSummary.setText(summary.toString());
    }
    
    private void checkSuit() {
        String id = txtSuitId.getText();
        HeroSuit suit = controller.findSuitById(id);

        if (suit == null) {
            lblSuitInfo.setText("No set found in the system!");
            btnRepair.setVisible(false);
            return;
        }

        lblSuitInfo.setText("SUIT: " + suit.getId() + " | SUIT_TYPE: " + suit.getType() + " | SUIT_Durability: " + suit.getDurability());

        boolean isValid = HeroSuitValidator.isValidSuit(suit);
        if (isValid) {
            txtCheckHistory.append("SUIT_ID: " + suit.getId() + " | Status: Passed\n");
            btnRepair.setVisible(false);
        } else {
            txtCheckHistory.append("SUIT_ID: " + suit.getId() + " | Status: Failed\n");

            int response = JOptionPane.showConfirmDialog(this,
                    "This suit does not meet the requirements. Do you want to repair it?",
                    "Repair suit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                btnRepair.setVisible(true);
            } else {
                btnRepair.setVisible(false);
            }
        }
    }

    private void repairSuit() {
        String id = txtSuitId.getText();
        HeroSuit suit = controller.findSuitById(id);
    
        // ซ่อมแซมชุดโดยเพิ่มความทนทาน (25 หน่วย สูงสุด 100)
        controller.repairSuit(id);
    
        // แสดงข้อความการซ่อมแซม
        lblSuitInfo.setText("The suit has been repaired! New durability: " + suit.getDurability() + "/100");
    
        // บันทึกประวัติการซ่อม
        txtRepairHistory.append("SUIT: " + suit.getId() + " | Repaired to: " + suit.getDurability() + "/100\n");
    
        // เพิ่มจำนวนการซ่อมแซมในประเภทชุด
        String suitType = suit.getType();
        repairCount.putIfAbsent(suitType, 0); // Initialize the count if it doesn't exist
        repairCount.put(suitType, repairCount.get(suitType) + 1);
    
        // อัปเดตข้อมูลการซ่อมแซม
        updateRepairSummary(repairCount);
    
        // อัปเดตข้อมูลชุดใหม่
        checkSuit();
    }
    

    // ฟังก์ชั่นที่ใช้เพื่อดึง ID จาก View
    public String getInputId() {
        return txtSuitId.getText();
    }

    // ฟังก์ชั่นที่ใช้เพื่อแสดงข้อความกรณีรหัสชุดไม่ถูกต้อง
    public void showInvalidMessage(String message) {
        lblSuitInfo.setText(message);
    }

    // ฟังก์ชั่นแสดงสถานะชุด
    public void showSuitStatus(HeroSuit suit) {
        lblSuitInfo.setText("SUIT: " + suit.getId() + " | SUIT_TYPE: " + suit.getType() + " | SUIT_Durability: " + suit.getDurability());
    }

    // ถามผู้ใช้สำหรับการซ่อมแซม
    public boolean askForRepair() {
        int response = JOptionPane.showConfirmDialog(this,
                "This set does not meet the requirements. Do you want to repair it?",
                "Repair kit",
                JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    // แสดงข้อความเมื่อการซ่อมแซมสำเร็จ
    public void showRepairSuccess() {
        JOptionPane.showMessageDialog(this,
                "Repair kit complete!",
                "Repair completed",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
}
