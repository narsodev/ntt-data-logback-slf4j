package com.nttdata;

import org.ejml.simple.SimpleMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/**
 * Main class of the application
 * 
 * @author Narciso González Calderón
 * @version 0.1-SNAPSHOT
 */
public class App {

  /** Logger Creation */
  private static final Logger LOG = LoggerFactory.getLogger(App.class);

  private static Scanner s;

  /**
   * @param text text to print in the console before the user input
   * @param min  minimum value of the input
   * @param max  maximum value of the input
   * @return the user input
   */
  public static int read(String text, int min, int max) {

    // Variables declaration
    int num = 0;
    boolean error;
    s = new Scanner(System.in);

    // Read the user input until it is correct
    do {
      System.out.print(text);
      try {
        LOG.info("Reading int from user");

        num = Integer.valueOf(s.next());
        LOG.debug("User input: {}", num);

        error = num < min || num > max;
      } catch (Exception e) {
        System.err.print(e);
        System.out.print(" .");
        error = true;
      }
      if (error) {
        System.out.println("Error.");
        LOG.error("Bad input");
      }
    } while (error);

    LOG.info("Input read");

    return num;
  }

  /**
   * @param args command line arguments (not used)
   * @throws Exception if an error occurs
   */
  public static void main(String[] args) throws Exception {
    LOG.info("Starting app");

    System.out.println("Generador de matrices:\n");

    // Read the number of rows and columns of the matrix

    LOG.info("Getting height and width");

    int height = read("Introduzca la altura (entero de 2 a 5): ", 2, 5);
    int width = read("Introduzca la anchura (entero de 2 a 5): ", 2, 5);

    LOG.info("Height and width read");

    LOG.debug("Height: {}", height);
    LOG.debug("Width: {}", width);

    // Generate the matrix with random values

    LOG.info("Generating matrix with random values");

    Random random = new Random();
    SimpleMatrix matrix = SimpleMatrix.random(height, width, 0, 10, random);
    String content;

    LOG.info("Matrix generated");

    int option;
    do {
      // Print the matrix
      LOG.info("Printing matrix");
      System.out.println("\nMatriz:");
      content = StringUtils.substringAfter(matrix.toString(), "\n");
      System.out.println(content);

      // Print menu
      LOG.info("Printing menu");
      System.out.println("Seleccione una de las siguientes opciones:");
      System.out.println("\t0. Salir.");
      System.out.println("\t1. Generar nueva matriz.");
      System.out.println("\t2. Calcular matriz inversa.");
      System.out.println("\t3. Generar PDF.");

      // Read the user input
      option = read("Opción: ", 0, 3);
      LOG.debug("Option selected: {}", option);

      // Execute the selected option
      LOG.info("Executing selected option");

      switch (option) {
      case 0:
        LOG.info("Stopping loop");
        System.out.println("Saliendo...");
        break;
      case 1:
        LOG.info("Getting height and width");

        height = read("Introduzca la altura (de 2 a 5): ", 2, 5);
        width = read("Introduzca la altura (de 2 a 5): ", 2, 5);

        LOG.info("Height and width read");

        LOG.info("Generating matrix with random values");

        random = new Random();
        matrix = SimpleMatrix.random(height, width, 0, 10, random);

        LOG.info("Matrix generated");
        break;
      case 2:
        // Calculate the inverse matrix (height and width must be equal)
        LOG.info("Calculating inverse matrix");
        if (height == width)
          try {
            matrix = matrix.invert();

            LOG.info("Inverse matrix calculated");

          } catch (Exception e) {
            System.out.println("Error calculando matriz inversa.");
            System.err.println(e);

            LOG.error("Error calculating inverse matrix");
          }
        else {
          System.err.println("Para generar la inversa de una matriz, esta debe ser cuadrada.");

          LOG.error("Error calculating inverse matrix");
        }
        break;
      case 3:
        // Generate the PDF
        LOG.info("Generating PDF");

        PDF matrixPdf = new PDF("Matriz generada:\n \n" + content, "Matriz.pdf");
        matrixPdf.save();

        LOG.info("PDF generated");
        break;
      }

      LOG.info("Selected option executed");

      if (option != 0)
        System.out.println("\n------------------------");
    } while (option != 0);

    s.close();
    
    for(int i = 0; i < 10000; i++)
       LOG.info("Closing app");
  }
}
