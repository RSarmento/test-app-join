package br.com.join.testAppJoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 3361563362225293950L;

    @Id
    @GeneratedValue(generator = "categorySequenceGenerator")
    @GenericGenerator(name = "categorySequenceGenerator",
            type = org.hibernate.id.enhanced.SequenceStyleGenerator.class,
            parameters = {
                    @Parameter(name = "sequence_name", value = "seq_category_id"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Please provide a name")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Please provide a description")
    @Size(min = 2, max = 100)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public ZonedDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; }

    public ZonedDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(ZonedDateTime updatedAt) { this.updatedAt = updatedAt; }
 }
