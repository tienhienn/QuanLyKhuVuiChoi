package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;

public class NhanVienPanel extends JPanel {
    // giữ nguyên khai báo biến…
    public JTextField tfMa, tfTen, tfNgaySinh, tfSDT, tfEmail, tfNgayBD, tfLuong, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim, btnClear, btnPhanCong, btnXemCa;
    public JTable table;
    public DefaultTableModel model;

    public NhanVienPanel() {

        /* ===== BẢNG MÀU ===== */
        Color blueLight  = new Color(227,242,253);  // #E3F2FD
        Color blueDeep   = new Color(30,136,229);   // #1E88E5

        setLayout(new BorderLayout(12,12));
        setBackground(blueLight);

        /* ===== PANEL NHẬP ===== */
        JPanel inputPanel = new JPanel(new GridLayout(7,2,10,4));
        inputPanel.setBackground(blueLight);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep,1,true),
                "Thông tin Nhân Viên",
                TitledBorder.LEFT,TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD,14),
                blueDeep));

        JTextField[] fields = {
                tfMa=new JTextField(), tfTen=new JTextField(),
                tfNgaySinh=new JTextField(), tfSDT=new JTextField(),
                tfEmail=new JTextField(), tfNgayBD=new JTextField(), tfLuong=new JTextField()
        };
        for(JTextField f:fields){
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(blueDeep,1,true),
                    BorderFactory.createEmptyBorder(2,4,2,4)));
            f.setPreferredSize(new Dimension(100,22));
            f.setFont(new Font("Segoe UI",Font.PLAIN,13));
        }

        inputPanel.add(new JLabel("Mã NV:"));                  inputPanel.add(tfMa);
        inputPanel.add(new JLabel("Tên NV:"));                 inputPanel.add(tfTen);
        inputPanel.add(new JLabel("Ngày sinh (dd/mm/yyyy):")); inputPanel.add(tfNgaySinh);
        inputPanel.add(new JLabel("SĐT:"));                    inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Email:"));                  inputPanel.add(tfEmail);
        inputPanel.add(new JLabel("Ngày bắt đầu (dd/mm/yyyy):"));inputPanel.add(tfNgayBD);
        inputPanel.add(new JLabel("Lương:"));                  inputPanel.add(tfLuong);

        add(inputPanel,BorderLayout.NORTH);
        
        for (JTextField f : fields) {
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(blueDeep, 1, true),
                    BorderFactory.createEmptyBorder(1,2,1,2)));
        }

        tfMa.addActionListener(e -> tfTen.requestFocus());
        tfTen.addActionListener(e -> tfNgaySinh.requestFocus());
        tfNgaySinh.addActionListener(e -> tfSDT.requestFocus());
        tfSDT.addActionListener(e -> tfEmail.requestFocus());
        tfEmail.addActionListener(e -> tfNgayBD.requestFocus());
        tfNgayBD.addActionListener(e -> tfLuong.requestFocus());

        /* ===== BẢNG ===== */
        model = new DefaultTableModel(new Object[]{
                "Mã NV","Tên","Ngày sinh","SĐT","Email","Ngày bắt đầu","Lương"},0);

        table = new JTable(model){
            public Component prepareRenderer(TableCellRenderer r,int row,int col){
                Component c=super.prepareRenderer(r,row,col);
                if(!isRowSelected(row))
                    c.setBackground(row%2==0?Color.WHITE:blueLight);
                else c.setBackground(new Color(144,202,249));
                return c;
            }
        };
        table.setRowHeight(24);
        table.setGridColor(new Color(200,200,200));
        JTableHeader th=table.getTableHeader();
        th.setBackground(blueDeep); th.setForeground(Color.WHITE);
        th.setFont(new Font("Segoe UI",Font.BOLD,14));
        th.setReorderingAllowed(false);

        JScrollPane sp=new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep,1,true),
                "Danh sách Nhân Viên",
                TitledBorder.LEFT,TitledBorder.TOP,
                new Font("Segoe UI",Font.BOLD,14),
                blueDeep));
        sp.getViewport().setBackground(Color.WHITE);
        add(sp,BorderLayout.CENTER);

        /* ===== PANEL CHỨC NĂNG ===== */
        JPanel control=new JPanel(new BorderLayout());
        control.setBackground(blueLight);

        JPanel search=new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.setBackground(blueLight);
        JPanel buttons=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,4));
        buttons.setBackground(blueLight);

        tfTimKiem=new JTextField(20);
        stylizeField(tfTimKiem,blueDeep);

        btnTim    =stylizeBtn(new JButton("Tìm"),blueDeep);
        btnThem   =stylizeBtn(new JButton("Thêm"),blueDeep);
        btnSua    =stylizeBtn(new JButton("Sửa"),blueDeep);
        btnXoa    =stylizeBtn(new JButton("Xóa"),blueDeep);
        btnClear  =stylizeBtn(new JButton("Làm mới"),blueDeep);

        btnPhanCong =stylizePill(new JButton("Phân công ca"),blueDeep);
        btnXemCa    =stylizePill(new JButton("Xem ca"),blueDeep);

        search.add(new JLabel("Tìm kiếm:"));
        search.add(tfTimKiem);
        search.add(btnTim);

        buttons.add(btnThem); buttons.add(btnSua);
        buttons.add(btnXoa);  buttons.add(btnClear);
        buttons.add(btnPhanCong); buttons.add(btnXemCa);

        control.add(search,BorderLayout.WEST);
        control.add(buttons,BorderLayout.EAST);
        add(control,BorderLayout.SOUTH);
    }

    /* ===== helpers ===== */
    private void stylizeField(JTextField f,Color borderCol){
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderCol,1,true),
                BorderFactory.createEmptyBorder(2,4,2,4)));
        f.setPreferredSize(new Dimension(100,22));
        f.setFont(new Font("Segoe UI",Font.PLAIN,13));
    }
    private JButton stylizeBtn(JButton b,Color bg){
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI",Font.PLAIN,14));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(6,16,6,16));
        b.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent e){ b.setBackground(new Color(100,181,246));}
            public void mouseExited (java.awt.event.MouseEvent e){ b.setBackground(bg);}
        });
        return b;
    }
    // pill style cho 2 hành động đặc biệt
    private JButton stylizePill(JButton b,Color bg){
        stylizeBtn(b,bg);
        b.setBorder(BorderFactory.createLineBorder(bg,1,true));
        b.setBackground(Color.WHITE);
        b.setForeground(bg);
        b.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent e){
                b.setBackground(bg); b.setForeground(Color.WHITE);}
            public void mouseExited(java.awt.event.MouseEvent e){
                b.setBackground(Color.WHITE); b.setForeground(bg);}
        });
        return b;
    }
}
