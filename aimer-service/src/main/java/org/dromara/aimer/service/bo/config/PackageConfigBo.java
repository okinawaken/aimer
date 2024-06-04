package org.dromara.aimer.service.bo.config;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import lombok.Data;

import java.util.Map;

@Data
public class PackageConfigBo {

    private String parent;

    private String moduleName;

    private String entity;

    private String service;

    private String serviceImpl;

    private String mapper;

    private String xml;

    private String controller;

    private Map<OutputFile, String> pathInfo;
}
