package net.sf.anathema.hero.combat.sheet.social.stats;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.framework.environment.Resources;

public class DeceptionStatsGroup extends AbstractSocialCombatsValueStatsGroup {

  public DeceptionStatsGroup(Resources resources) {
    super(resources, "Deception");
  }

  @Override
  public void addContent(PdfPTable table, Font font, ISocialCombatStats stats) {
    if (stats == null) {
      table.addCell(createFinalValueCell(font));
      table.addCell(createFinalValueCell(font));
    } else {
      table.addCell(createFinalValueCell(font, stats.getDeceptionAttackValue()));
      table.addCell(createFinalValueCell(font, stats.getDeceptionMDV()));
    }
  }

  @Override
  public int getColumnCount() {
    return 2;
  }
}
