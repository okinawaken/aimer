package org.dromara.aimer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bixiu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBodyDTO {

    /**
     * 租户ID
     */
    private String tenantId;

}
