package main.filter;

public interface AbstractFilter<T> {
    boolean accept(T entity);
}