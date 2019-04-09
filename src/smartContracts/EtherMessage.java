package smartContracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class EtherMessage extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506108a9806100206000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c8063114bc4561461006757806314c0d38e1461014c5780634c6d9157146101fd57806356bb15ad146102ae578063a62f536b146102f2578063b581318314610320575b600080fd5b61014a6004803603606081101561007d57600080fd5b810190808035906020019064010000000081111561009a57600080fd5b8201836020820111156100ac57600080fd5b803590602001918460018302840111640100000000831117156100ce57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061034e565b005b6101826004803603604081101561016257600080fd5b810190808035906020019092919080359060200190929190505050610463565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101c25780820151818401526020810190506101a7565b50505050905090810190601f1680156101ef5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102336004803603604081101561021357600080fd5b81019080803590602001909291908035906020019092919050505061056f565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610273578082015181840152602081019050610258565b50505050905090810190601f1680156102a05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102f0600480360360208110156102c457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061067b565b005b61031e6004803603602081101561030857600080fd5b8101908080359060200190929190505050610726565b005b61034c6004803603602081101561033657600080fd5b810190808035906020019092919050505061077f565b005b60008060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001905060008060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020190508482600001600084600101548152602001908152602001600020600001600085815260200190815260200160002090805190602001906104199291906107d8565b5084816000016000836001015481526020019081526020016000206000016000858152602001908152602001600020908051906020019061045b9291906107d8565b505050505050565b60606000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201600001600084815260200190815260200160002060000160008381526020019081526020016000208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105625780601f1061053757610100808354040283529160200191610562565b820191906000526020600020905b81548152906001019060200180831161054557829003601f168201915b5050505050905092915050565b60606000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001600001600084815260200190815260200160002060000160008381526020019081526020016000208054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561066e5780601f106106435761010080835404028352916020019161066e565b820191906000526020600020905b81548152906001019060200180831161065157829003601f168201915b5050505050905092915050565b6000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001600101600081548092919060010191905055506000808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206002016001016000815480929190600101919050555050565b6000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160000160008281526020019081526020016000205050565b6000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020160000160008281526020019081526020016000205050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061081957805160ff1916838001178555610847565b82800160010185558215610847579182015b8281111561084657825182559160200191906001019061082b565b5b5090506108549190610858565b5090565b61087a91905b8082111561087657600081600090555060010161085e565b5090565b9056fea165627a7a723058209f8984b2fa5a6daafc474e928889d31464c4cf0f0fe27d5e2eba03e489dd6e100029";

    public static final String FUNC_BUILDMESSAGE = "buildMessage";

    public static final String FUNC_GETINBOXMESSAGE = "getInboxMessage";

    public static final String FUNC_GETOUTBOXMESSAGE = "getOutboxMessage";

    public static final String FUNC_SUBMITMESSAGE = "submitMessage";

    public static final String FUNC_DELETEOUTBOXMESSAGE = "deleteOutboxMessage";

    public static final String FUNC_DELETEINBOXMESSAGE = "deleteInboxMessage";

    @Deprecated
    protected EtherMessage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EtherMessage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EtherMessage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EtherMessage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> buildMessage(String text, String recipient, BigInteger block_num) {
        final Function function = new Function(
                FUNC_BUILDMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(text), 
                new org.web3j.abi.datatypes.Address(recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(block_num)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getInboxMessage(BigInteger index, BigInteger block_num) {
        final Function function = new Function(FUNC_GETINBOXMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index), 
                new org.web3j.abi.datatypes.generated.Uint256(block_num)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getOutboxMessage(BigInteger index, BigInteger block_num) {
        final Function function = new Function(FUNC_GETOUTBOXMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index), 
                new org.web3j.abi.datatypes.generated.Uint256(block_num)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> submitMessage(String recipient) {
        final Function function = new Function(
                FUNC_SUBMITMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(recipient)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deleteOutboxMessage(BigInteger index) {
        final Function function = new Function(
                FUNC_DELETEOUTBOXMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deleteInboxMessage(BigInteger index) {
        final Function function = new Function(
                FUNC_DELETEINBOXMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EtherMessage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EtherMessage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EtherMessage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EtherMessage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EtherMessage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EtherMessage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EtherMessage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EtherMessage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EtherMessage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EtherMessage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EtherMessage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EtherMessage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EtherMessage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EtherMessage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EtherMessage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EtherMessage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
