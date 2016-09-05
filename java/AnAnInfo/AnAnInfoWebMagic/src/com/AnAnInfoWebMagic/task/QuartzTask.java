package com.AnAnInfoWebMagic.task;

/**
 * 任务基类，所有任务类都应该继承此类
 * @author wxb
 *
 */
public interface QuartzTask {
    public void work(String[] args);
}
