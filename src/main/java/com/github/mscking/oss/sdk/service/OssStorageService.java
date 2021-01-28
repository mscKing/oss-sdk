package com.github.mscking.oss.sdk.service;

import com.github.mscking.oss.common.constant.AccessControlEnum;
import com.github.mscking.oss.common.constant.CommonConstant;
import com.github.mscking.oss.common.handler.DownloadHandler;
import com.github.mscking.oss.common.model.BytesRecord;
import com.github.mscking.oss.common.model.FileObjectInfo;
import com.github.mscking.oss.common.model.StorageBucket;
import com.github.mscking.oss.rpc.model.FileOperationRequest;
import com.github.mscking.oss.rpc.model.FileReadRequest;
import com.github.mscking.oss.rpc.model.FileWriteRequest;

import javax.jws.WebParam;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author miaosc
 * @date 2020/10/4 0004
 */
public interface OssStorageService {

    /**
     * 查询所有的存储桶
     * <p>
     * 一个APP最多设置64个桶
     * </p>
     *
     * @return 桶列表
     */
    List<StorageBucket> listAllBuckets();

    /**
     * 检查指定名称的存储桶是否存在
     *
     * @param bucketName 桶名称
     * @return 存在返回<code>true</code>,否则返回<code>false</code>
     */
    boolean checkBuckExist(String bucketName);

    /**
     * 创建桶
     *
     * @param bucketName    桶名称
     * @param description   桶描述
     * @param accessControl 访问权限
     * @return 桶对象
     */
    StorageBucket createStorageBucket(String bucketName, String description, AccessControlEnum accessControl);

    /**
     * 修改桶的访问权限
     *
     * @param bucketName    桶名称
     * @param accessControl 访问权限
     * @return 桶对象
     */
    StorageBucket setStorageBucketACL(String bucketName, AccessControlEnum accessControl);

    /**
     * 删除桶
     *
     * @param bucketName 桶名称
     */
    void deleteBucket(String bucketName);

    /**
     * 读取文件
     *
     * @param fileReadRequest 读取请求
     * @param downloadHandler 下载回调
     * @throws IOException 发生IO错误时
     */
    void readFile(FileReadRequest fileReadRequest, DownloadHandler downloadHandler) throws IOException;

    /**
     * 创建空文件
     * <p>
     * 针对大文件
     * </p>
     *
     * @param bucketName 桶名称
     * @param fileName   文件名
     * @return 文件对象信息
     */
    FileObjectInfo createEmptyFile(String bucketName, String fileName);

    /**
     * 追加文件
     *
     * @param fileWriteRequest 文件写入请求
     * @param inputStream      文件输入流
     * @return 写入信息
     * @throws IOException
     */
    BytesRecord appendFile(FileWriteRequest fileWriteRequest, InputStream inputStream) throws IOException;

    /**
     * 直接上传文件
     * <p>
     * 主要针对小文件写入,速度会快一些
     * </p>
     *
     * @param bucketName  桶名称
     * @param fileName    文件名称,可以为null
     * @param inputStream 文件输入流
     * @return 文件对象信息
     * @throws IOException
     */
    FileObjectInfo writeFileDirectly(String bucketName, String fileName, @WebParam InputStream inputStream) throws IOException;

    /**
     * 查询文件
     *
     * @param fileOperationRequest 请求
     * @return 文件对象信息
     */
    FileObjectInfo findFileInfo(FileOperationRequest fileOperationRequest);

    /**
     * 删除文件
     *
     * @param fileOperationRequest 请求
     */
    void deleteFile(FileOperationRequest fileOperationRequest);


    /**
     * 给url带上访问的签名参数,注意签名是有时效的,{@link CommonConstant#SIGN_VALID_PERIOD}
     *
     * @param bucketName 桶名称
     * @param fileId     文件id
     * @return url签名参数,eg: random=aaa&timestamp=16732737237&sign=ABEF120314
     */
    String buildSignParam(String bucketName, String fileId);

    /**
     * 下载地址
     * @param bucketName
     * @param fileId
     * @return
     */
    String buildDownloadUrl(String bucketName, String fileId);

    /**
     * 批量删除文件
     *
     * @param fileOperationRequests 要删除的集合
     */
    void deleteFilesBatch(Collection<FileOperationRequest> fileOperationRequests);

    /**
     * 统计bucket下面有多少文件
     *
     * @param bucketName bucket名称
     * @return 文件数量
     */
    long countBucketFiles(String bucketName);
}
