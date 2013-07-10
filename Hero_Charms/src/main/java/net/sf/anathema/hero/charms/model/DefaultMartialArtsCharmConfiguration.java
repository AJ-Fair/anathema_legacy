package net.sf.anathema.hero.charms.model;

import net.sf.anathema.character.main.magic.model.charm.Charm;
import net.sf.anathema.character.main.template.magic.MartialArtsCharmConfiguration;
import net.sf.anathema.hero.charms.CharmsModel;
import net.sf.anathema.hero.experience.ExperienceModel;
import net.sf.anathema.character.main.magic.model.charms.ILearningCharmGroup;
import net.sf.anathema.hero.magic.MagicCollection;

import java.util.HashSet;
import java.util.Set;

import static net.sf.anathema.character.main.magic.model.charms.MartialArtsUtilities.MARTIAL_ARTS;
import static net.sf.anathema.character.main.magic.model.charms.MartialArtsUtilities.hasLevel;
import static net.sf.anathema.character.main.magic.model.charm.CharmAttributeList.NO_STYLE_ATTRIBUTE;
import static net.sf.anathema.character.main.magic.model.charm.MartialArtsLevel.Celestial;

public class DefaultMartialArtsCharmConfiguration implements MartialArtsCharmConfiguration {
  private final CharmsModel configuration;
  private final MagicCollection collection;
  private ExperienceModel experience;

  public DefaultMartialArtsCharmConfiguration(CharmsModel configuration, MagicCollection collection, ExperienceModel experience) {
    this.configuration = configuration;
    this.collection = collection;
    this.experience = experience;
  }

  @Override
  public Charm[] getLearnedCharms() {
    return configuration.getLearnedCharms(experience.isExperienced());
  }

  @Override
  public String[] getIncompleteCelestialMartialArtsGroups() {
    return getIncompleteCelestialMartialArtsGroups(getMartialArtsGroups());
  }

  @Override
  public String[] getCompleteCelestialMartialArtsGroups() {
    Set<String> completedGroups = new HashSet<>();
    for (ILearningCharmGroup group : getMartialArtsGroups()) {
      Charm martialArtsCharm = group.getCoreCharms()[0];
      if (isCelestialStyle(martialArtsCharm) && isCompleted(group)) {
        completedGroups.add(group.getId());
      }
    }
    return completedGroups.toArray(new String[completedGroups.size()]);
  }

  @Override
  public boolean isAnyCelestialStyleCompleted() {
    return isAnyCelestialMartialArtsGroupCompleted(getMartialArtsGroups());
  }

  private String[] getIncompleteCelestialMartialArtsGroups(ILearningCharmGroup[] groups) {
    Set<String> uncompletedGroups = new HashSet<>();
    for (ILearningCharmGroup group : groups) {
      Charm martialArtsCharm = group.getCoreCharms()[0];
      if (!isCelestialStyle(martialArtsCharm) || isCompleted(group)) {
        continue;
      }
      if (isBegun(group)) {
        uncompletedGroups.add(group.getId());
      }
    }
    return uncompletedGroups.toArray(new String[uncompletedGroups.size()]);
  }

  private boolean isAnyCelestialMartialArtsGroupCompleted(ILearningCharmGroup[] groups) {
    for (ILearningCharmGroup group : groups) {
      Charm martialArtsCharm = group.getCoreCharms()[0];
      if (isCelestialStyle(martialArtsCharm) && isCompleted(group)) {
        return true;
      }
    }
    return false;
  }

  private boolean isCelestialStyle(Charm martialArtsCharm) {
    return hasLevel(Celestial, martialArtsCharm) && !martialArtsCharm.hasAttribute(NO_STYLE_ATTRIBUTE);
  }

  private boolean isBegun(ILearningCharmGroup group) {
    for (Charm charm : group.getAllCharms()) {
      if (group.isLearned(charm)) {
        return true;
      }
    }
    return false;
  }

  private boolean isCompleted(ILearningCharmGroup group) {
    for (Charm charm : group.getCoreCharms()) {
      if (!group.isLearned(charm) && !charm.isBlockedByAlternative(collection)) {
        return false;
      }
    }
    return true;
  }

  private ILearningCharmGroup[] getMartialArtsGroups() {
    return configuration.getCharmGroups(MARTIAL_ARTS);
  }
}