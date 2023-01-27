package br.com.j38.poi.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Valid
@ApiModel("POI DTO")
public class POIDTO {
    @ApiModelProperty(value = "Name")
    @NotNull(message = "Name")
    private String name;
    @ApiModelProperty(value = "Radius")
    @Min(value = 0, message = "Radius")
    @Max(value = 1000, message = "Radius")
    @NotNull(message = "Radius")
    private int radius;
    @ApiModelProperty(value = "Coordinates")
    @NotNull(message = "Coordinates")
    private List<Double> coordinates;
}
