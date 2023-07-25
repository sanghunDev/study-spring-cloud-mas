package com.app.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass // 부모클래스는 테이블이랑 매핑하지 않음(자식 클래스에 매핑 정보만 제공)
@Getter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
