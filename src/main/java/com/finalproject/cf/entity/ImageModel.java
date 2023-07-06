package com.finalproject.cf.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE_MODEL")
@NoArgsConstructor
@Data
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;

    @Column(length = 50000000)
    private byte[] picBytes;

    public ImageModel(String name, String type, byte[] picBytes) {
        this.name = name;
        this.type = type;
        this.picBytes = picBytes;
    }
}
