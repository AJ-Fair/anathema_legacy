package net.sf.anathema.lib.gui.table.columsettings;

import com.google.common.base.Preconditions;
import net.sf.anathema.lib.gui.table.celleditors.NullValueStrategy;
import net.sf.anathema.lib.gui.table.celleditors.NumberCellEditor;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberTableColumnSettings extends AbstractTableColumnSettings {

  private final NumberFormat format;
  private final NullValueStrategy nullValueStrategy;
  private final Class<?> valueClass;

  public static NumberTableColumnSettings getDoubleInstance(String format, NullValueStrategy nullValueStrategy) {
    return new NumberTableColumnSettings(new DecimalFormat(format), Double.class, nullValueStrategy);
  }

  public static NumberTableColumnSettings getIntegerInstance(String format, NullValueStrategy nullValueStrategy) {
    return new NumberTableColumnSettings(new DecimalFormat(format), Integer.class, nullValueStrategy);
  }

  public NumberTableColumnSettings(NumberFormat format, Class<?> valueClass) {
    this(format, valueClass, NullValueStrategy.DISALLOW);
  }

  public NumberTableColumnSettings(NumberFormat format, Class<?> valueClass, NullValueStrategy nullValueStrategy) {
    super(format.getMinimumIntegerDigits() + format.getMinimumFractionDigits() + 2);
    Preconditions.checkNotNull(format);
    Preconditions.checkNotNull(valueClass);
    Preconditions.checkNotNull(nullValueStrategy);
    this.format = format;
    this.valueClass = valueClass;
    this.nullValueStrategy = nullValueStrategy;
  }

  @Override
  public TableCellEditor getEditor() {
    return new NumberCellEditor(format, valueClass, nullValueStrategy);
  }

  @Override
  public TableCellRenderer getRenderer() {
    return new DefaultTableCellRenderer() {
      private static final long serialVersionUID = 7660668794478659982L;

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return super.getTableCellRendererComponent(table, value == null ? "" : format.format(value), //$NON-NLS-1$
                isSelected, hasFocus, row, column);
      }
    };
  }
}