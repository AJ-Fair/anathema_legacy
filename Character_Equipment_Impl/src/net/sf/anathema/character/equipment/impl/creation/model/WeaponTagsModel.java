package net.sf.anathema.character.equipment.impl.creation.model;

import java.util.EnumMap;
import java.util.Map;

import net.sf.anathema.character.equipment.creation.model.IWeaponTagsModel;
import net.sf.anathema.lib.workflow.booleanvalue.BooleanValueModel;

public class WeaponTagsModel implements IWeaponTagsModel {

  private final Map<WeaponTag, BooleanValueModel> tagMap = new EnumMap<WeaponTag, BooleanValueModel>(WeaponTag.class);

  public WeaponTagsModel() {
    for (WeaponTag tag : WeaponTag.values()) {
      tagMap.put(tag, new BooleanValueModel(false));
    }
  }
}