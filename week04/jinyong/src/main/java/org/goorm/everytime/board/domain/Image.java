package org.goorm.everytime.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private long size;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Image(String fileName, String fileType, long size, byte[] data, Post post) {
        this.name = fileName;
        this.type = fileType;
        this.size = size;
        this.data = data;
        this.post = post;
    }
}