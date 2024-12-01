package com.szakdologzat.repiceapp.service.dto;

import com.szakdologzat.repiceapp.domain.enumeration.Unit;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.szakdologzat.repiceapp.domain.Ingredient} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IngredientDTO implements Serializable {

    private Long id;

    private String name;

    private Integer amount;

    private Unit unit;

    private InstructionDTO instruction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public InstructionDTO getInstruction() {
        return instruction;
    }

    public void setInstruction(InstructionDTO instruction) {
        this.instruction = instruction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientDTO ingredientDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ingredientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            ", unit='" + getUnit() + "'" +
            ", instruction=" + getInstruction() +
            "}";
    }
}
