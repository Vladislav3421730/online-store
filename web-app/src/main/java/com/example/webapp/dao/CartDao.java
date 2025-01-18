package com.example.webapp.dao;

public interface CartDao {
   void incrementAmount(Long cartId);
   void decrementAmount(Long cartId);
}
