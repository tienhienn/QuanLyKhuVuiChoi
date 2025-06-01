package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Cursor;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.HoaDonController;
import Model.HoaDon;

public class HoaDonPanel extends JPanel {

    private JTable tblHoaDon;
    private DefaultTableModel model;
    private JButton btnXemChiTiet, btnXuatPhieu, btnLamMoi, btnXuatExcel;
    private HoaDonController hoaDonController;

    public HoaDonPanel(Connection conn) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));

        hoaDonController = new HoaDonController(conn);

        // Panel chính với gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 242, 245), 0, h, new Color(255, 255, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel với tiêu đề
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Hóa Đơn");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 136, 229));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("CÔNG TY DU LỊCH KHÔNG VUI THÌ CHƠI");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitleLabel.setForeground(new Color(100, 100, 100));
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subTitleLabel);
        headerPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table Panel với shadow effect
        JPanel tableWrapper = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ shadow
                int shadowSize = 5;
                int shadowOffset = 3;
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 15, 15);

                // Vẽ background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);
            }
        };
        tableWrapper.setLayout(new BorderLayout());
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        tableWrapper.setOpaque(false);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Mã Đặt Vé", "Tên Khách Hàng", "Ngày Đặt", "Số Người", "Tổng Tiền (VND)"
        });

        tblHoaDon = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    comp.setBackground(row % 2 == 0 ? new Color(240, 247, 255) : Color.WHITE);
                } else {
                    comp.setBackground(new Color(179, 229, 255));
                }
                return comp;
            }
        };

        // Styling cho table
        tblHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblHoaDon.setRowHeight(35);
        tblHoaDon.setShowGrid(false);
        tblHoaDon.setIntercellSpacing(new Dimension(0, 0));
        
        // Header styling
        JTableHeader header = tblHoaDon.getTableHeader();
        header.setBackground(new Color(30, 136, 229));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Center align for all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
            tblHoaDon.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tblHoaDon);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableWrapper.add(scrollPane);

        mainPanel.add(tableWrapper, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        btnXemChiTiet = new JButton("Xem chi tiết");
        btnLamMoi = new JButton("Làm mới");
        btnXuatExcel = new JButton("Xuất doanh thu");

        // Style các button
        styleButton(btnXemChiTiet);
        styleButton(btnLamMoi);
        styleButton(btnXuatExcel);

        buttonPanel.add(btnXemChiTiet);
        buttonPanel.add(btnLamMoi);
        buttonPanel.add(btnXuatExcel);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Action listeners
        btnXemChiTiet.addActionListener(e -> xuLyXemChiTiet());
        btnLamMoi.addActionListener(e -> loadData());
        btnXuatExcel.addActionListener(e -> xuatDoanhThuExcel());

        loadData();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 136, 229));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(21, 101, 192));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 136, 229));
            }
        });
    }

    private void xuLyXemChiTiet() {
        int selectedRow = tblHoaDon.getSelectedRow();
        List<HoaDon> list = hoaDonController.getAllHoaDon();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem chi tiết.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maDatVe = model.getValueAt(selectedRow, 0).toString();

        HoaDon hd = null;
        for (HoaDon h : list) {
            if (h.getMaDatVe().equals(maDatVe)) {
                hd = h;
                break;}
        }
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin chi tiết cho hóa đơn đã chọn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo dialog mới để hiển thị chi tiết
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chi tiết hóa đơn", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(240, 240, 240));

        // Panel chính với màu nền trắng và shadow effect
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ shadow
                int shadowGap = 5;
                int shadowOffset = 3;
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowGap - 1, getHeight() - shadowGap - 1, 15, 15);

                // Vẽ background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - shadowGap, getHeight() - shadowGap, 15, 15);
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        // Header với logo hoặc tên công ty
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("HÓA ĐƠN DỊCH VỤ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 136, 229));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitleLabel = new JLabel("CÔNG TY DU LỊCH KHÔNG VUI THÌ CHƠI");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subTitleLabel.setForeground(new Color(100, 100, 100));
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subTitleLabel);
        headerPanel.add(Box.createVerticalStrut(20));

        // Thông tin hóa đơn
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Format date
        java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("dd/MM/yyyy");

        // Thêm thông tin với style
        addInfoField(infoPanel, gbc, "Mã Đặt Vé:", hd.getMaDatVe(), 0);
        addInfoField(infoPanel, gbc, "Tên Khách Hàng:", hd.getTenKhachHang(), 1);
        addInfoField(infoPanel, gbc, "Ngày Đặt:", hd.getNgayDat() != null ? sdfDate.format(hd.getNgayDat()) : "", 2);
        addInfoField(infoPanel, gbc, "Ngày Đi:", hd.getNgayDi() != null ? sdfDate.format(hd.getNgayDi()) : "", 3);

        // Panel cho thông tin số lượng người
        JPanel quantityPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        quantityPanel.setOpaque(false);
        
        addQuantityBox(quantityPanel, "Người Lớn", hd.getNguoiLon());
        addQuantityBox(quantityPanel, "Trẻ Em", hd.getTreEm());
        addQuantityBox(quantityPanel, "Người Già", hd.getNguoiGia());

        // Panel cho thông tin giá
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setOpaque(false);
        pricePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(200, 200, 200)));

        addPriceRow(pricePanel, "Giá Tour (1 người):", hd.getGiaTour());
        addPriceRow(pricePanel, "Thành Tiền Tour:", hd.getThanhTienTour());
        addPriceRow(pricePanel, "Thành Tiền Dịch Vụ:", hd.getThanhTienDichVu());
        
        // Tổng tiền với style đặc biệt
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setOpaque(false);
        JLabel totalLabel = new JLabel("Tổng Tiền:");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel totalValue = new JLabel(String.format("%,.0f VNĐ", hd.getTongTien()));
        totalValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalValue.setForeground(new Color(30, 136, 229));
        totalPanel.add(totalLabel);
        totalPanel.add(Box.createHorizontalStrut(10));
        totalPanel.add(totalValue);

        // Thêm tất cả vào main panel
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(quantityPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(pricePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(totalPanel);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("Cảm ơn quý khách đã sử dụng dịch vụ!");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(footerLabel);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(footerPanel, BorderLayout.SOUTH);
        
        dialog.setSize(500, 650);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addInfoField(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridy = row;
        
        gbc.gridx = 0;
        JLabel lblField = new JLabel(label);
        lblField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lblField, gbc);

        gbc.gridx = 1;
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblValue, gbc);
    }

    private void addQuantityBox(JPanel panel, String title, int quantity) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel quantityLabel = new JLabel(String.valueOf(quantity));
        quantityLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        quantityLabel.setForeground(new Color(30, 136, 229));
        quantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(Box.createVerticalStrut(5));
        box.add(titleLabel);
        box.add(Box.createVerticalStrut(5));
        box.add(quantityLabel);
        box.add(Box.createVerticalStrut(5));

        panel.add(box);
    }

    private void addPriceRow(JPanel panel, String label, double amount) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        row.setOpaque(false);
        
        JLabel lblTitle = new JLabel(label);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblAmount = new JLabel(String.format("%,.0f VNĐ", amount));
        lblAmount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        row.add(lblTitle);
        row.add(Box.createHorizontalStrut(10));
        row.add(lblAmount);
        
        panel.add(row);
    }

    private void loadData() {
        model.setRowCount(0);
        List<HoaDon> list = hoaDonController.getAllHoaDon();
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu hóa đơn để hiển thị.");
            return;
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        for (HoaDon hd : list) {
            String thangNam = "";
            if (hd.getNgayDat() != null) {
                thangNam = sdf.format(hd.getNgayDat());
            }
            model.addRow(new Object[]{
                    hd.getMaDatVe(),
                    hd.getTenKhachHang(),
                    thangNam,
                    hd.getNguoiLon() + hd.getTreEm() + hd.getNguoiGia(), 
                    String.format("%,.0f", hd.getTongTien())
            });
        }
    }

    private void xuatDoanhThuExcel() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File("DoanhThu.xlsx"));

            if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".xlsx")) {
                file = new File(file.getParentFile(), file.getName() + ".xlsx");
            }

            // Tạo workbook mới
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Doanh Thu");

            // Tạo font và style cho header
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Style cho các ô dữ liệu
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            // Style cho số tiền
            CellStyle moneyStyle = workbook.createCellStyle();
            moneyStyle.cloneStyleFrom(dataStyle);
            DataFormat format = workbook.createDataFormat();
            moneyStyle.setDataFormat(format.getFormat("#,##0"));

            // Style cho ngày tháng
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.cloneStyleFrom(dataStyle);
            dateStyle.setDataFormat(format.getFormat("dd/mm/yyyy"));

            // Tạo header
            Row headerRow = sheet.createRow(0);
            String[] columns = {
                "Mã Đặt Vé", "Tên Khách Hàng", "Ngày Đặt", "Ngày Đi",
                "Người Lớn", "Trẻ Em", "Người Già",
                "Giá Tour", "Thành Tiền Tour", "Thành Tiền Dịch Vụ", "Tổng Tiền"
            };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 15 * 256); // 15 ký tự
            }

            // Thêm dữ liệu
            List<HoaDon> list = hoaDonController.getAllHoaDon();
            int rowNum = 1;
            double tongDoanhThu = 0;

            java.text.SimpleDateFormat sdfExcel = new java.text.SimpleDateFormat("dd/MM/yyyy");
            for (HoaDon hd : list) {
                Row row = sheet.createRow(rowNum++);

                // Cột thông tin cơ bản
                createCell(row, 0, hd.getMaDatVe(), dataStyle);
                createCell(row, 1, hd.getTenKhachHang(), dataStyle);
                
                // Ngày tháng
                if (hd.getNgayDat() != null) {
                    Cell cellNgayDat = row.createCell(2);
                    cellNgayDat.setCellValue(sdfExcel.format(hd.getNgayDat()));
                    cellNgayDat.setCellStyle(dateStyle);
                }
                if (hd.getNgayDi() != null) {
                    Cell cellNgayDi = row.createCell(3);
                    cellNgayDi.setCellValue(sdfExcel.format(hd.getNgayDi()));
                    cellNgayDi.setCellStyle(dateStyle);
                }

                // Số lượng người
                createCell(row, 4, hd.getNguoiLon(), dataStyle);
                createCell(row, 5, hd.getTreEm(), dataStyle);
                createCell(row, 6, hd.getNguoiGia(), dataStyle);

                // Các loại tiền
                createCell(row, 7, hd.getGiaTour(), moneyStyle);
                createCell(row, 8, hd.getThanhTienTour(), moneyStyle);
                createCell(row, 9, hd.getThanhTienDichVu(), moneyStyle);
                createCell(row, 10, hd.getTongTien(), moneyStyle);

                tongDoanhThu += hd.getTongTien();
            }

            // Thêm dòng tổng doanh thu
            Row totalRow = sheet.createRow(rowNum + 1);
            Cell labelCell = totalRow.createCell(0);
            labelCell.setCellValue("TỔNG DOANH THU:");
            labelCell.setCellStyle(headerStyle);

            Cell totalCell = totalRow.createCell(10);
            totalCell.setCellValue(tongDoanhThu);
            totalCell.setCellStyle(moneyStyle);

            // Auto size các cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Lưu file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(this,
                    "Xuất file Excel thành công!\nĐường dẫn: " + file.getAbsolutePath(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            }

            workbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất file Excel: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createCell(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
        cell.setCellStyle(style);
    }
}
