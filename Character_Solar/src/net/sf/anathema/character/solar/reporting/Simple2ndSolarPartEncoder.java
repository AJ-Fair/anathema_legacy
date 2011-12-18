package net.sf.anathema.character.solar.reporting;

import net.sf.anathema.character.reporting.pdf.rendering.general.box.IPdfContentBoxEncoder;
import net.sf.anathema.character.reporting.pdf.layout.simple.AbstractSecondEditionExaltPdfPartEncoder;
import net.sf.anathema.character.reporting.pdf.layout.simple.SimpleEncodingRegistry;
import net.sf.anathema.lib.resources.IResources;

public class Simple2ndSolarPartEncoder extends AbstractSecondEditionExaltPdfPartEncoder {

  public Simple2ndSolarPartEncoder(IResources resources, SimpleEncodingRegistry registry, int essenceMax) {
    super(resources, registry, essenceMax);
  }

  public IPdfContentBoxEncoder getGreatCurseEncoder() {
    return new PdfSolarVirtueFlawEncoder(getBaseFont());
  }

  @Override
  public IPdfContentBoxEncoder getAnimaEncoder() {
    return new SolarAnimaEncoderFactory(getResources(), getBaseFont(), getSymbolBaseFont()).createAnimaEncoder();
  }
}