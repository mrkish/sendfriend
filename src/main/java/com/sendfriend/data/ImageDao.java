package com.sendfriend.data;

import com.sendfriend.models.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageDao extends CrudRepository<Image, Integer> {
}
