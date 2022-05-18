package com.nttdata;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * PDF Object class
 * 
 * @author Narciso González Calderón
 * @version 0.1-SNAPSHOT
 */
public class PDF {
  // Define font properties of the document
  final private PDFont FONT = PDType1Font.HELVETICA_BOLD;
  final private int FONT_SIZE = 20;

  private String name;
  private String text;

  /**
   * Constructor
   * 
   * @param text text to print in the PDF
   * @param name name of the PDF
   */
  public PDF(String text, String name) {
    this.name = name;
    this.text = text;
  }

  /**
   * Save PDF
   */
  public void save() {
    // Separate text by lines and store each line as an element of an array
    String[] lines = StringUtils.split(StringUtils.remove(this.text, '\r'), '\n');

    // Create the PDF document
    try {
      PDDocument document = new PDDocument();
      PDPage page = new PDPage();
      document.addPage(page);
      PDPageContentStream contentStream = new PDPageContentStream(document, page);

      // Begin the pdf content
      contentStream.beginText();
      contentStream.setFont(FONT, FONT_SIZE);
      contentStream.setLeading(FONT_SIZE + 0.5f);
      contentStream.newLineAtOffset(100, 700);

      // Print lines, each line on a new line
      for (String line : lines) {
        contentStream.showText(line);
        contentStream.newLine();
      }

      // End the pdf content
      contentStream.endText();
      contentStream.close();

      // Save the PDF document
      document.save(this.name);
      document.close();

      System.out.printf("Documento guardado: %s.\n", this.name);
    } catch (Exception e) {
      // Show error message
      System.err.println("Error guardando PDF.");
      System.err.println(e);
    }
  }
}
