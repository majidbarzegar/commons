package com.penovatech.common.model;

import jakarta.validation.groups.Default;


public class ValidationGroup {
    public interface Save extends Default {
    }

    public interface Update extends Default {
    }
}
