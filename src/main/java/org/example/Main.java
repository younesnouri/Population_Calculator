package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;




/**
 * Population Projection Calculator GUI application.
 * Calculates population projections using exponential growth formulas.
 */
public class Main {

  /**
     * Main entry point. Initializes and shows the GUI.
     *
     * @param args command line arguments (ignored)
     */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Main::createAndShowGui);

  }


  private static final String TIME_FORMAT = """
      Time required: %.2f years

      It will take %.2f years for the population to grow
      from %,.0f to %,.0f people.

      Formula used: x = log(A/a) / log(b)
      Where: A = %.0f, a = %.0f, b = %.3f
      """;

  private static final String RATE_FORMAT = """
      Annual Growth Rate (r): %.4f%% per year                                 \s
      This means the population grows by %.4f%% each year.
                                           \s
      Formula used: r = (A/a)^(1/x) - 1
      Where: A = %.0f, a = %.0f, x = %.0f
     \s""";

  private static final String POPULATION_FORMAT = """
      Projected population after %.0f years: %,.0f people
      Population increase: %+,.0f people
      Percentage growth: %.2f%%
                                           \s
      Formula used: A = a × b^x
      Where: a = %.0f, b = %.3f, x = %.0f""";

  private static final String FUTURE_POPULATION = "Future Population (A)";
  private static final String INITIAL_POPULATION = "Initial Population (a):";
  private static final String GROWTH_RATE = "Growth Rate (r)";
  private static final String GROWTH_FACTOR = "Growth Factor (b):";
  private static final String TIME = "Time (x)";
  private static final String TIME_X = "Time (x) in years:";
  private static final String SEGOE = "Segoe UI";
  private static final Logger logger = Logger.getLogger(Main.class.getName());



  /**
     * Creates and displays the main application window with all UI components.
     */

  private static void createAndShowGui() {
    logger.info("Application Version: " + Version.FULL_VERSION);

    JFrame frame = new JFrame("Population Projection Calculator");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(900, 700);
    frame.setLocationRelativeTo(null); // Center on screen

    JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
    mainPanel.setBackground(new Color(245, 247, 250));
    mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

    JPanel titlePanel = createTitlePanel();
    JPanel inputPanel = createInputPanel();
    JPanel resultPanel = createResultPanel();

    mainPanel.add(titlePanel, BorderLayout.NORTH);
    mainPanel.add(inputPanel, BorderLayout.CENTER);
    mainPanel.add(resultPanel, BorderLayout.SOUTH);

    frame.setContentPane(mainPanel);
    frame.setVisible(true);
  }

  /**
     * Creates the panel containing the title and subtitle labels.
     *
     * @return JPanel with title components
     */
  private static JPanel createTitlePanel() {
    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    titlePanel.setBackground(new Color(245, 247, 250));

    JLabel titleLabel = new JLabel("Population Projection Calculator");
    titleLabel.setFont(new Font(SEGOE, Font.BOLD, 28));
    titleLabel.setForeground(new Color(51, 51, 51));

    JLabel subtitleLabel =
                new JLabel("Calculate future population growth with exponential models");
    subtitleLabel.setFont(new Font(SEGOE, Font.PLAIN, 14));
    subtitleLabel.setForeground(new Color(102, 102, 102));

    JPanel titleContainer = new JPanel();
    titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
    titleContainer.setBackground(new Color(245, 247, 250));

    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    titleContainer.add(titleLabel);
    titleContainer.add(Box.createRigidArea(new Dimension(0, 5)));
    titleContainer.add(subtitleLabel);

    titlePanel.add(titleContainer);
    return titlePanel;

  }
  /**
     * Creates the main input panel with fields, buttons, and results area.
     * Handles input validation, user interaction, and calculation logic.
     *
     * @return JPanel containing inputs and results
     */

  private static JPanel createInputPanel() {
    JPanel cardPanel = new JPanel();
    cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
    cardPanel.setBackground(Color.WHITE);
    cardPanel.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(230, 230, 230), 1, true),
                        new EmptyBorder(30, 30, 30, 30)));

    // Calculation type selection
    JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    choicePanel.setBackground(Color.WHITE);

    JLabel choiceLabel = new JLabel("What would you like to calculate?");
    choiceLabel.setFont(new Font(SEGOE, Font.BOLD, 16));
    choiceLabel.setForeground(new Color(51, 51, 51));

    String[] options = {FUTURE_POPULATION, GROWTH_RATE, TIME};
    JComboBox<String> choiceBox = new JComboBox<>(options);
    choiceBox.setFont(new Font(SEGOE, Font.PLAIN, 14));
    choiceBox.setPreferredSize(new Dimension(250, 35));

    choicePanel.add(choiceLabel);
    choicePanel.add(Box.createRigidArea(new Dimension(20, 0)));
    choicePanel.add(choiceBox);

    // Input fields panel
    JPanel fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
    fieldsPanel.setBackground(Color.WHITE);
    fieldsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

    JLabel label1 = createStyledLabel(INITIAL_POPULATION);
    JLabel label2 = createStyledLabel(GROWTH_FACTOR);
    JLabel label3 = createStyledLabel(TIME_X);

    JTextField field1 = createStyledTextField();
    JTextField field2 = createStyledTextField();
    JTextField field3 = createStyledTextField();

    fieldsPanel.add(label1);
    fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    fieldsPanel.add(field1);
    fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    fieldsPanel.add(label2);
    fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    fieldsPanel.add(field2);
    fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    fieldsPanel.add(label3);
    fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    fieldsPanel.add(field3);

    // Buttons panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
    buttonPanel.setBackground(Color.WHITE);

    JButton calculateBtn = createStyledButton("Calculate", new Color(70, 130, 220));
    JButton resetBtn = createStyledButton("Reset", new Color(108, 117, 125));

    buttonPanel.add(calculateBtn);
    buttonPanel.add(resetBtn);

    // Result area
    JTextArea resultArea = new JTextArea(20, 50);
    resultArea.setEditable(false);
    resultArea.setFont(new Font(SEGOE, Font.PLAIN, 14));
    resultArea.setBackground(new Color(248, 249, 250));
    resultArea.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(206, 212, 218), 1),
                        new EmptyBorder(15, 15, 15, 15)));

    JPanel resultContainer = new JPanel(new BorderLayout());
    resultContainer.setBackground(Color.WHITE);
    resultContainer.setBorder(
                BorderFactory.createTitledBorder(
                        new LineBorder(new Color(206, 212, 218), 1),
                        "Results",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font(SEGOE, Font.BOLD, 14),
                        new Color(73, 80, 87)));
    resultContainer.add(resultArea, BorderLayout.CENTER);

    cardPanel.add(choicePanel);
    cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    cardPanel.add(fieldsPanel);
    cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    cardPanel.add(buttonPanel);
    cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    cardPanel.add(resultContainer);

    // Event handlers
    choiceBox.addActionListener(e -> {
      String choice = (String) choiceBox.getSelectedItem();
      if (FUTURE_POPULATION.equals(choice)) {
        label1.setText(INITIAL_POPULATION);
        label2.setText(GROWTH_FACTOR);
        label3.setText(TIME_X);
      } else if (GROWTH_RATE.equals(choice)) {
        label1.setText(INITIAL_POPULATION);
        label2.setText("Future Population (A):");
        label3.setText(TIME_X);
      } else if (TIME.equals(choice)) {
        label1.setText(INITIAL_POPULATION);
        label2.setText("Future Population (A):");
        label3.setText(GROWTH_FACTOR);
      }

      field1.setText("");
      field2.setText("");
      field3.setText("");
      resultArea.setText("");
    });

    resetBtn.addActionListener(e -> {
      field1.setText("");
      field2.setText("");
      field3.setText("");
      resultArea.setText("");
    });



    calculateBtn.addActionListener(e -> {
      String choice = (String) choiceBox.getSelectedItem();
      try {
        double a = parsePositiveDouble(field1.getText());
        double second = parsePositiveDouble(field2.getText());
        double third = parsePositiveDouble(field3.getText());

        if (FUTURE_POPULATION.equals(choice)) {
          if (second <= 1.0) {
            throw new ArithmeticException("Growth factor cannot be 1.");
          }
          double a1 = a * power(second, third);
          double increase = a1 - a;
          double percentIncrease = (increase / a) * 100;
          resultArea.setText(
                            String.format(
                                    POPULATION_FORMAT,
                                    third, a1, increase, percentIncrease, a, second, third));
        } else if (GROWTH_RATE.equals(choice)) {
          double result = root(second / a, third) - 1;
          resultArea.setText(
                            String.format(
                                    RATE_FORMAT,
                                    result * 100, result * 100, second, a, third));
        } else {
          if (third <= 1.0) {
            throw new ArithmeticException("Growth factor cannot be 1.");
          }
          double result = log(second / a) / log(third);
          resultArea.setText(
                  String.format(TIME_FORMAT, result, result, a, second, second, a, third)
          );
        }
      } catch (Exception ex) {
        resultArea.setText(
                        ex.getMessage()
                                + "\n"
                                + "Please check your inputs and try again.\n"
                                + "• All values must be positive numbers\n"
                                + "• Growth factor cannot be 1 when calculating time\n"
                                + "• Use decimal notation (e.g., 1.05 for 5% growth)");
      }
    });

    // Initialize
    choiceBox.setSelectedIndex(0);
    choiceBox.getActionListeners()[0].actionPerformed(null);

    return cardPanel;
  }

  /**
     * Creates the panel with a tip label shown below the input panel.
     *
     * @return JPanel containing the tip
     */
  private static JPanel createResultPanel() {
    JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    resultPanel.setBackground(new Color(245, 247, 250));

    JLabel infoLabel = new JLabel(" Tip: Use growth factors like 1.05 for 5% annual growth");
    infoLabel.setFont(new Font(SEGOE, Font.ITALIC, 12));
    infoLabel.setForeground(new Color(108, 117, 125));

    resultPanel.add(infoLabel);
    return resultPanel;
  }

  /**
     * Creates a styled JLabel for input field descriptions.
     *
     * @param text label text
     * @return styled JLabel
     */
  private static JLabel createStyledLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("SansSerif", Font.BOLD, 16));
    label.setForeground(new Color(73, 80, 87));
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    return label;
  }

  /**
     * Creates a styled JTextField with border and focus effects.
     *
     * @return styled JTextField
     */
  private static JTextField createStyledTextField() {
    JTextField field = new JTextField(1);
    field.setFont(new Font(SEGOE, Font.PLAIN, 14));
    field.setPreferredSize(new Dimension(2, 3));
    field.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(206, 212, 218), 1),
                        new EmptyBorder(5, 10, 5, 10)));

    // Add focus effects
    field.addFocusListener(
                new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
              field.setBorder(
                                new CompoundBorder(
                                        new LineBorder(new Color(70, 130, 220), 2),
                                        new EmptyBorder(4, 9, 4, 9)));
            }

            @Override
            public void focusLost(FocusEvent e) {
              field.setBorder(
                                new CompoundBorder(
                                        new LineBorder(new Color(206, 212, 218), 1),
                                        new EmptyBorder(5, 10, 5, 10)));
            }
          });

    return field;
  }

  /**
     * Creates a styled JButton with given colors and hover effects.
     *
     * @param text    button label
     * @param bgColor background color
     * @return styled JButton
     */
  private static JButton createStyledButton(String text, Color bgColor) {
    JButton button = new JButton(text);
    button.setFont(new Font(SEGOE, Font.BOLD, 14));
    button.setPreferredSize(new Dimension(120, 40));
    button.setBackground(bgColor);
    button.setForeground(Color.WHITE);
    button.setBorder(new EmptyBorder(10, 20, 10, 20));
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Add hover effects
    button.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          button.setBackground(bgColor.darker());
        }

        @Override
        public void mouseExited(MouseEvent e) {
          button.setBackground(bgColor);
        }
      });

    return button;
  }

  /**
     * Parses a string input and ensures it represents a positive double value.
     *
     * @param input string input
     * @return parsed positive double value
     * @throws Exception if input is empty, not a number, or not positive
     */
  private static double parsePositiveDouble(String input) throws Exception {
    if (input == null || input.trim().isEmpty()) {
      throw new Exception("Input cannot be empty.");
    }

    double val;
    try {
      val = Double.parseDouble(input.trim());
    } catch (NumberFormatException e) {
      throw new Exception("Input must be a valid number.");
    }
    if (val <= 0) {
      throw new Exception("Input must be positive.");
    }
    return val;
  }

  /**
     * Calculates base raised to the exponent, supporting fractional exponents.
     *
     * @param base     base value
     * @param exponent exponent value
     * @return base^exponent
     */
  private static double power(double base, double exponent) {
    double result = 1.0;
    for (int i = 0; i < (int) exponent; i++) {
      result *= base;
    }
    double fraction = exponent - (int) exponent;
    if (fraction > 0) {
      result *= exp(fraction * log(base));
    }
    return result;
  }

  /**
     * Calculates the nth root of 'a'.
     *
     * @param a the number to take the root of
     * @param n the root degree
     * @return nth root of a
     */
  private static double root(double a, double n) {
    return exp(log(a) / n);
  }

  /**
     * Calculates the natural logarithm of x using a series expansion.
     *
     * @param x value greater than 1
     * @return natural logarithm of x
     * @throws ArithmeticException if x <= 1
     */
  private static double log(double x) {
    if (x <= 1) {
      throw new ArithmeticException("Log input must be > 1.");
    }
    double result = 0.0;
    double y = (x - 1) / (x + 1);
    for (int i = 1; i < 1000; i += 2) {
      double term = 1.0 / i;
      result += term * power(y, i);
    }
    return 2 * result;
  }

  /**
     * Calculates e raised to the power x using a series expansion.
     *
     * @param x exponent
     * @return e^x
     */
  private static double exp(double x) {
    double result = 1.0;
    double term = 1.0;
    for (int i = 1; i < 30; i++) {
      term *= x / i;
      result += term;
    }
    return result;
  }
}
