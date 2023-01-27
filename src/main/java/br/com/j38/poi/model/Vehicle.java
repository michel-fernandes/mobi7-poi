package br.com.j38.poi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
@ApiModel("Vehicle")
@Document(collection = "vehicle")
public class Vehicle {
    @Id
    @ApiModelProperty(value = "Vehicle ID")
    @NotNull(message = "Vehicle ID")
    private String id;
    private String plate;

}
