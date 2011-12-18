package net.sf.anathema.character.reporting.pdf.rendering.boxes.experience;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.PdfTextEncodingUtilities;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants;
import net.sf.anathema.lib.resources.IResources;

import java.text.MessageFormat;

public class PdfExperienceEncoder implements IPdfContentBoxEncoder {

  private static final int FONT_SIZE = IVoidStateFormatConstants.FONT_SIZE;
  private final Font font;
  private final IResources resources;

  public PdfExperienceEncoder(IResources resources, BaseFont baseFont) {
    this.resources = resources;
    this.font = PdfTextEncodingUtilities.createFont(baseFont, FONT_SIZE);
  }

  public String getHeaderKey(IGenericCharacter character, IGenericDescription description) {
    return "Experience"; //$NON-NLS-1$
  }

  public void encode(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description,
                     Bounds bounds) throws DocumentException {
    int totalPoints = character.getTotalExperiencePoints();
    int spentPoints = character.getSpentExperiencePoints();
    String experienceMessage = resources.getString("Sheet.Experience.MessageFormat"); //$NON-NLS-1$
    String experienceText = MessageFormat.format(experienceMessage, totalPoints, spentPoints, totalPoints - spentPoints);
    Phrase phrase = new Phrase(experienceText, font);
    PdfTextEncodingUtilities.encodeText(directContent, phrase, bounds, FONT_SIZE + 4);
  }

  public boolean hasContent(IGenericCharacter character) {
    return true;
  }
}