/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.response.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.*;

/**
 *
 * @author acer
 */
@Data
@AllArgsConstructor
public class PagingResponseModel {
    private List<?> data;
    private long totalResult;
    private int totalPage;
    private int currentPage;
}
