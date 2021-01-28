package com.github.mscking.oss.rpc;

import com.github.mscking.oss.common.model.BytesRecord;
import com.github.mscking.oss.common.model.StorageBucket;
import com.github.mscking.oss.common.constant.AccessControlEnum;
import com.github.mscking.oss.common.model.FileObjectInfo;
import com.github.mscking.oss.rpc.model.FileOperationRequest;
import com.github.mscking.oss.rpc.model.FileWriteRequest;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 存储调用客户端
 *
 * @author miaosc
 * @date 2020/9/26 0026
 */
@WebService(targetNamespace = "http://rpc.oss.wecarry.com")
public interface StorageClient {

    /**
     * 查询所有的存储桶
     * <p>
     * 一个APP最多设置64个桶
     * </p>
     *
     * @return 桶列表
     */
    @WebMethod
    List<StorageBucket> listAllBuckets();

    /**
     * 检查指定名称的存储桶是否存在
     *
     * @param bucketName 桶名称
     * @return 存在返回<code>true</code>,否则返回<code>false</code>
     */
    @WebMethod
    boolean checkBuckExist(String bucketName);

    /**
     * 创建桶
     *
     * @param bucketName    桶名称
     * @param description   桶描述
     * @param accessControl 访问权限
     * @return 桶对象
     */
    @WebMethod
    StorageBucket createStorageBucket(String bucketName, String description, AccessControlEnum accessControl);

    /**
     * 修改桶的访问权限
     *
     * @param bucketName    桶名称
     * @param accessControl 访问权限
     * @return 桶对象
     */
    @WebMethod
    StorageBucket setStorageBucketACL(String bucketName, AccessControlEnum accessControl);

    /**
     * 删除桶
     *
     * @param bucketName 桶名称
     */
    @WebMethod
    void deleteBucket(String bucketName);

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
    @WebMethod
    FileObjectInfo createEmptyFile(String bucketName, String fileName);

    /**
     * 追加文件
     *
     * @param fileWriteRequest 文件写入请求
     * @param dataHandler      文件附件
     * @return 写入信息
     * @throws IOException
     */
    @WebMethod
    BytesRecord appendFile(FileWriteRequest fileWriteRequest, DataHandler dataHandler) throws IOException;

    /**
     * 写入文件
     * <p>
     * 主要针对小文件写入,速度会快一些
     * </p>
     *
     * @param bucketName  桶名称
     * @param fileName    文件名称,可以为null
     * @param dataHandler 文件附件
     * @return 文件对象信息
     * @throws IOException
     */
    @WebMethod
    FileObjectInfo writeFile(String bucketName, String fileName, DataHandler dataHandler) throws IOException;

    /**
     * 查询文件
     *
     * @param fileOperationRequest 请求
     * @return 文件对象信息
     */
    @WebMethod
    FileObjectInfo findFileInfo(FileOperationRequest fileOperationRequest);

    /**
     * 删除文件
     *
     * @param fileOperationRequest 请求
     */
    @WebMethod
    void deleteFile(FileOperationRequest fileOperationRequest);

    /**
     * 批量删除文件
     *
     * @param fileOperationRequests 要删除的集合
     */
    @WebMethod
    void deleteFilesBatch(Collection<FileOperationRequest> fileOperationRequests);

    /**
     * 统计bucket下面有多少文件
     * @param bucketName bucket名称
     * @return 文件数量
     */
    long countBucketFiles(String bucketName);
}
