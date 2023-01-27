package br.com.j38.poi.DTO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Valid
@ApiModel("Tracking DTO")
public class TrackingDTO {
    @ApiModelProperty(value = "Date ISO 8601")
    @NotNull(message = "Date")
    private LocalDateTime positionDate;
    @ApiModelProperty(value = "Velocity")
    @NotNull(message = "Velocity")
    private int velocity;
    @ApiModelProperty(value = "Ignition on")
    @NotNull(message = "Ignition on")
    private boolean ignition;
    @ApiModelProperty(value = "Coordinates")
    @NotNull(message = "Coordinates")
    private List<Double> coordinates;
}
