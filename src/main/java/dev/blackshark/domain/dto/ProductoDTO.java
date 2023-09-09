package dev.blackshark.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProductoDTO {

    @NotBlank
    private String nombre;

    @Min(0)
    private float precio;

    public ProductoDTO(@NotBlank String nombre, @Min(0) float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
