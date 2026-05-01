import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Main UI window for managing Assets. (Beautified)
 */
public class AssetUI extends JFrame {

    private AssetDAO assetDAO;
    private JTable tableAssets;
    private DefaultTableModel tableModel;

    // Form inputs
    private JTextField txtAssetName, txtAssetType, txtAssignedTo, txtSearch;
    private JComboBox<String> cbCondition;

    // Colors & Fonts
    private final Color PRIMARY_COLOR = new Color(41, 128, 185); // Beautiful Blue
    private final Color BACKGROUND_COLOR = new Color(245, 247, 250); // Light gray/blue
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public AssetUI() {
        assetDAO = new AssetDAO();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Department Asset Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        initComponents();
        loadTableData();
    }

    private void initComponents() {
        // --- HEADER ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(PRIMARY_COLOR);
        panelHeader.setPreferredSize(new Dimension(getWidth(), 60));
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JLabel lblHeader = new JLabel("Asset Management Dashboard");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(Color.WHITE);
        panelHeader.add(lblHeader);
        add(panelHeader, BorderLayout.NORTH);

        // --- MAIN BODY ---
        JPanel panelBody = new JPanel(new BorderLayout(15, 15));
        panelBody.setBackground(BACKGROUND_COLOR);
        panelBody.setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- TOP PANEL: Add Asset Form ---
        JPanel panelForm = new JPanel(new GridLayout(2, 4, 15, 10));
        panelForm.setBackground(Color.WHITE);
        TitledBorder formBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1), "Register New Asset");
        formBorder.setTitleFont(BOLD_FONT);
        formBorder.setTitleColor(PRIMARY_COLOR);
        panelForm.setBorder(BorderFactory.createCompoundBorder(formBorder, new EmptyBorder(10, 10, 10, 10)));

        panelForm.add(createStyledLabel("Asset Name:"));
        txtAssetName = createStyledTextField();
        panelForm.add(txtAssetName);

        panelForm.add(createStyledLabel("Asset Type:"));
        txtAssetType = createStyledTextField();
        panelForm.add(txtAssetType);

        panelForm.add(createStyledLabel("Condition:"));
        cbCondition = new JComboBox<>(new String[]{"Good", "Fair", "Damaged", "Under Repair"});
        cbCondition.setFont(MAIN_FONT);
        cbCondition.setBackground(Color.WHITE);
        panelForm.add(cbCondition);

        panelForm.add(createStyledLabel("Assigned To:"));
        txtAssignedTo = createStyledTextField();
        panelForm.add(txtAssignedTo);

        panelBody.add(panelForm, BorderLayout.NORTH);

        // --- CENTER PANEL: Table + Search ---
        JPanel panelCenter = new JPanel(new BorderLayout(0, 10));
        panelCenter.setBackground(BACKGROUND_COLOR);
        
        // Search bar
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelSearch.setBackground(BACKGROUND_COLOR);
        panelSearch.add(createStyledLabel("Search by Name:"));
        txtSearch = createStyledTextField();
        txtSearch.setPreferredSize(new Dimension(250, 30));
        panelSearch.add(txtSearch);
        
        JButton btnSearch = createStyledButton("Search", PRIMARY_COLOR);
        JButton btnRefresh = createStyledButton("Refresh", new Color(46, 204, 113)); // Emerald green
        panelSearch.add(btnSearch);
        panelSearch.add(btnRefresh);

        panelCenter.add(panelSearch, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"ID", "Asset Name", "Type", "Condition", "Assigned To", "Added Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableAssets = new JTable(tableModel);
        tableAssets.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableAssets.setRowHeight(28);
        tableAssets.setSelectionBackground(new Color(189, 224, 254)); // Light blue selection
        tableAssets.setShowVerticalLines(false);
        tableAssets.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader tableHeader = tableAssets.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(PRIMARY_COLOR);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setPreferredSize(new Dimension(100, 35));

        JScrollPane scrollPane = new JScrollPane(tableAssets);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        panelBody.add(panelCenter, BorderLayout.CENTER);
        add(panelBody, BorderLayout.CENTER);

        // --- BOTTOM PANEL: Actions ---
        JPanel panelActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelActions.setBackground(Color.WHITE);
        panelActions.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
        
        JButton btnAdd = createStyledButton("Add Asset", PRIMARY_COLOR);
        JButton btnUpdateCondition = createStyledButton("Update Condition", new Color(243, 156, 18)); // Orange
        JButton btnDelete = createStyledButton("Delete Asset", new Color(231, 76, 60)); // Red
        JButton btnExport = createStyledButton("Export to CSV", new Color(142, 68, 173)); // Purple

        panelActions.add(btnAdd);
        panelActions.add(btnUpdateCondition);
        panelActions.add(btnDelete);
        panelActions.add(btnExport);

        add(panelActions, BorderLayout.SOUTH);

        // --- ACTION LISTENERS ---

        btnAdd.addActionListener(e -> {
            String name = txtAssetName.getText().trim();
            String type = txtAssetType.getText().trim();
            String condition = (String) cbCondition.getSelectedItem();
            String assignedTo = txtAssignedTo.getText().trim();

            if (name.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Type are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Asset asset = new Asset(0, name, type, condition, assignedTo, new java.sql.Date(System.currentTimeMillis()));
            if (assetDAO.addAsset(asset)) {
                JOptionPane.showMessageDialog(this, "Asset successfully registered!");
                clearForm();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add asset.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            String query = txtSearch.getText().trim();
            populateTable(assetDAO.searchAssetsByName(query));
        });

        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadTableData();
        });

        btnUpdateCondition.addActionListener(e -> {
            int selectedRow = tableAssets.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an asset from the table.");
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String currentCondition = (String) tableModel.getValueAt(selectedRow, 3);
            String[] conditions = {"Good", "Fair", "Damaged", "Under Repair"};
            String newCondition = (String) JOptionPane.showInputDialog(
                    this, "Select new condition:", "Update Condition", JOptionPane.QUESTION_MESSAGE, null, conditions, currentCondition);

            if (newCondition != null && !newCondition.equals(currentCondition)) {
                if (assetDAO.updateCondition(id, newCondition)) {
                    JOptionPane.showMessageDialog(this, "Condition updated!");
                    loadTableData();
                }
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = tableAssets.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an asset from the table.");
                return;
            }
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            if (JOptionPane.showConfirmDialog(this, "Delete asset: " + name + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (assetDAO.deleteAsset(id)) {
                    loadTableData();
                }
            }
        });

        btnExport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath().endsWith(".csv") ? file.getAbsolutePath() : file.getAbsolutePath() + ".csv";
                if (assetDAO.exportToCSV(path)) {
                    JOptionPane.showMessageDialog(this, "Exported successfully to " + path);
                }
            }
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BOLD_FONT);
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(MAIN_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(BOLD_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        return button;
    }

    private void clearForm() {
        txtAssetName.setText("");
        txtAssetType.setText("");
        cbCondition.setSelectedIndex(0);
        txtAssignedTo.setText("");
    }

    private void loadTableData() {
        populateTable(assetDAO.getAllAssets());
    }

    private void populateTable(List<Asset> assets) {
        tableModel.setRowCount(0);
        for (Asset asset : assets) {
            tableModel.addRow(new Object[]{asset.getId(), asset.getAssetName(), asset.getAssetType(), asset.getCondition(), asset.getAssignedTo(), asset.getAddedDate()});
        }
    }
}
