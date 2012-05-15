package net.sf.anathema.lib.gui.dialog.widgets;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class TextBlockFactory {

  public static List<TextBlock> createTextBlocks(final String text) {
    Preconditions.checkNotNull(text);
    int endIndex = 0;
    int startIndex = 0;
    final ArrayList<TextBlock> blocks = new ArrayList<TextBlock>();
    while (endIndex < text.length()) {
      final TextBlockDelimiter delimiter = TextBlockDelimiter.forCharacterOrNull(text
          .charAt(endIndex));
      if (delimiter != null) {
        final int actualEndIndex = delimiter.isAdditionalCharacterIncluded()
            ? endIndex + 1
            : endIndex;
        final String blockText = text.substring(startIndex, actualEndIndex);
        if (endIndex == text.length() - 1) {
          blocks.add(new TextBlock(blockText, TextBlockDelimiter.END_OF_TEXT));
        }
        else {
          blocks.add(new TextBlock(blockText, delimiter));
        }
        ++endIndex;
        startIndex = endIndex;
      }
      else {
        ++endIndex;
      }
    }
    if (endIndex > startIndex) {
      blocks
          .add(new TextBlock(text.substring(startIndex, endIndex), TextBlockDelimiter.END_OF_TEXT));
    }
    return blocks;
  }
}