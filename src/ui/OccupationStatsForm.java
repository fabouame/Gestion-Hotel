/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import services.ReservationService;
import entities.Reservation;
import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;



/**
 *
 * @author USER
 */
public class OccupationStatsForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form OccupationStatsForm
     */
    private ReservationService rs = new ReservationService();

    public OccupationStatsForm() {
        initComponents();
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(800, 600);
        buildChart();
    }

    private void buildChart() {
        // 1) Calculate occupation per month
        Map<YearMonth, Integer> joursReserves = new HashMap<>();

        for (Reservation r : rs.findAll()) {
            LocalDate d1 = r.getDateDebut();
            LocalDate d2 = r.getDateFin();

            // Loop day by day (simple, good enough for small project)
            LocalDate d = d1;
            while (!d.isAfter(d2)) {
                YearMonth ym = YearMonth.from(d);
                joursReserves.put(ym, joursReserves.getOrDefault(ym, 0) + 1);
                d = d.plusDays(1);
            }
        }

        // 2) Build dataset with more detailed information
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series = "Jours réservés";

        // Also calculate percentages for tooltips
        Map<YearMonth, Double> tauxMap = new HashMap<>();

        for (Map.Entry<YearMonth, Integer> e : joursReserves.entrySet()) {
            YearMonth ym = e.getKey();
            int reservedDays = e.getValue();
            int totalDays = ym.lengthOfMonth();
            double taux = (reservedDays * 100.0) / totalDays;

            tauxMap.put(ym, taux);

            String monthLabel = ym.getMonthValue() + "/" + ym.getYear();
            dataset.addValue(taux, series, monthLabel);
        }

        // 3) Create chart with better formatting
        JFreeChart chart = ChartFactory.createBarChart(
                "Taux d'occupation des chambres par mois", // More descriptive title
                "Mois",
                "Taux d'occupation (%)",
                dataset
        );

        // 4) Customize the chart for better readability
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(70, 130, 180)); // Steel blue color

        // Set background colors
        chart.getCategoryPlot().setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(Color.WHITE);

        // Add grid lines for better readability
        chart.getCategoryPlot().setDomainGridlinePaint(Color.LIGHT_GRAY);
        chart.getCategoryPlot().setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Customize the title font
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));

        // Customize axis labels
        chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 12));
        chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 12));

        // Add value labels on top of bars
        chart.getCategoryPlot().getRenderer().setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        chart.getCategoryPlot().getRenderer().setDefaultItemLabelsVisible(true);
        chart.getCategoryPlot().getRenderer().setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 10));

        // 5) Add a subtitle with summary statistics
        if (!joursReserves.isEmpty()) {
            double maxTaux = tauxMap.values().stream().max(Double::compare).orElse(0.0);
            double minTaux = tauxMap.values().stream().min(Double::compare).orElse(0.0);
            double avgTaux = tauxMap.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

            String subtitleText = String.format("Moyenne: %.1f%% | Max: %.1f%% | Min: %.1f%%", 
                                              avgTaux, maxTaux, minTaux);

            org.jfree.chart.title.TextTitle subtitle = new org.jfree.chart.title.TextTitle(subtitleText);
            subtitle.setFont(new Font("SansSerif", Font.ITALIC, 12));
            subtitle.setPaint(Color.DARK_GRAY);
            chart.addSubtitle(subtitle);
        }

        // 6) Put chart into Swing panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400)); // Reduced size to fit current layout
        chartPanel.setPopupMenu(null); // Disable default popup

        chartPanelContainer.setLayout(new java.awt.BorderLayout());
        chartPanelContainer.removeAll();
        chartPanelContainer.add(chartPanel, java.awt.BorderLayout.CENTER);
        chartPanelContainer.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chartPanelContainer = new javax.swing.JPanel();

        chartPanelContainer.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(chartPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(318, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(chartPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPanelContainer;
    // End of variables declaration//GEN-END:variables
}
