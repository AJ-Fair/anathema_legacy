package net.sf.anathema.character.sidereal.reporting.rendering;

import static net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants.TEXT_PADDING;

import net.sf.anathema.character.generic.character.*;
import net.sf.anathema.character.generic.impl.rules.ExaltedEdition;
import net.sf.anathema.character.library.virtueflaw.model.IVirtueFlaw;
import net.sf.anathema.character.library.virtueflaw.presenter.IVirtueFlawModel;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.virtueflaw.VirtueFlawBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.elements.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.table.TableEncodingUtilities;
import net.sf.anathema.character.reporting.pdf.rendering.general.AbstractPdfEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.PdfEncodingUtilities;
import net.sf.anathema.character.reporting.pdf.rendering.general.PdfTextEncodingUtilities;
import net.sf.anathema.character.reporting.pdf.rendering.page.IVoidStateFormatConstants;
import net.sf.anathema.character.sidereal.paradox.SiderealParadoxTemplate;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;

public class SiderealParadoxEncoder extends AbstractPdfEncoder implements IPdfContentBoxEncoder {

  private final BaseFont baseFont;
  private final IResources resources;
  private final VirtueFlawBoxEncoder traitEncoder;
  private final Chunk symbolChunk;

  public SiderealParadoxEncoder(BaseFont baseFont, BaseFont symbolBaseFont, IResources resources) {
    this.baseFont = baseFont;
    this.resources = resources;
    this.traitEncoder = new VirtueFlawBoxEncoder(baseFont);
    this.symbolChunk = PdfEncodingUtilities.createCaretSymbolChunk(symbolBaseFont);
  }

  @Override
  protected BaseFont getBaseFont() {
    return baseFont;
  }

  public void encode(PdfContentByte directContent, IGenericCharacter character, IGenericDescription description, Bounds bounds) throws DocumentException {
	IVirtueFlaw virtueFlaw = ((IVirtueFlawModel) character.getAdditionalModel(SiderealParadoxTemplate.ID)).getVirtueFlaw();
    Bounds textBounds = traitEncoder.encode(directContent, bounds, virtueFlaw.getLimitTrait().getCurrentValue());
    float lineHeight = (textBounds.height - TEXT_PADDING) / 2;
    String effects = resources.getString("Sheet.GreatCurse.Sidereal.CurrentEffects") + ":"; //$NON-NLS-1$ //$NON-NLS-2$
    drawLabelledContent(
        directContent,
        effects,
        null,
        new Position(textBounds.x, textBounds.getMaxY() - lineHeight),
        bounds.width);

    Font font = TableEncodingUtilities.createFont(getBaseFont());
    Phrase phrase = new Phrase("", font); //$NON-NLS-1$
    phrase.add(symbolChunk);
    phrase.add(resources.getString("Sheet.GreatCurse.Sidereal." +
    		(character.getRules().getEdition() == ExaltedEdition.SecondEdition ?
    		 "2E." : "") + "RulesPages")); //$NON-NLS-1$
    Bounds infoBounds = new Bounds(bounds.x, bounds.y, bounds.width, textBounds.height - lineHeight);
    encodeTextWithReducedLineHeight(directContent, infoBounds, phrase);
  }

  public String getHeaderKey(IGenericCharacter character, IGenericDescription description) {
    return "GreatCurse.Sidereal"; //$NON-NLS-1$
  }
  
  public boolean hasContent(IGenericCharacter character)
  {
	  return true;
  }
}