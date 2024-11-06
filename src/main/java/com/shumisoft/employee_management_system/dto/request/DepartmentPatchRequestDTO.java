package com.shumisoft.employee_management_system.dto.request;

import lombok.Data;

@Data
public class DepartmentPatchRequestDTO {

    private String name;
    private String description;

    public boolean isEmpty() {

        return name == null && description == null;

    }
}
