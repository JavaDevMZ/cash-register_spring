package com.javadevMZ.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


public enum Role {

    ADMIN,
    CASHIER,
    SENIOR_CASHIER,
    COMMODITY_EXPERT
}
