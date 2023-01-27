package br.com.j38.poi.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Valid
@ApiModel("Vehicle DTO")
public class VehicleDTO {
    @ApiModelProperty(value = "Plate license")
    @NotBlank
    @Length(min = 7, max = 8, message = "Plate license")
    private String plate;
}
