package com.eu.habbo.messages.outgoing.roleplay.character;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacterSkills;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class CharacterSkillsDataComposer extends MessageComposer {
    private final RoleplayCharacterSkills characterSkills;

    public CharacterSkillsDataComposer(RoleplayCharacterSkills characterSkills) {
        this.characterSkills = characterSkills;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.CharacterSkillsDataComposer);
        this.response.appendInt(this.characterSkills.getCharacterId());
        this.response.appendInt(this.characterSkills.getStrengthLevel());
        this.response.appendInt(this.characterSkills.getStrengthExperience());
        this.response.appendInt(this.characterSkills.getStaminaLevel());
        this.response.appendInt(this.characterSkills.getStaminaExperience());
        this.response.appendInt(this.characterSkills.getAgilityLevel());
        this.response.appendInt(this.characterSkills.getAgilityExperience());
        this.response.appendInt(this.characterSkills.getResilienceLevel());
        this.response.appendInt(this.characterSkills.getResilienceExperience());
        this.response.appendInt(this.characterSkills.getMeleeLevel());
        this.response.appendInt(this.characterSkills.getMeleeExperience());
        this.response.appendInt(this.characterSkills.getRangedLevel());
        this.response.appendInt(this.characterSkills.getRangedExperience());
        this.response.appendInt(this.characterSkills.getDefenseLevel());
        this.response.appendInt(this.characterSkills.getDefenseExperience());
        return this.response;
    }
}