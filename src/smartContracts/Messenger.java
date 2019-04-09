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
public class Messenger extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610407806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c8063551524e7146100465780639a846adb146100d8578063c48d6d5e146100f5575b600080fd5b6100636004803603602081101561005c57600080fd5b50356101a8565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561009d578181015183820152602001610085565b50505050905090810190601f1680156100ca5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610063600480360360208110156100ee57600080fd5b5035610250565b6101a66004803603604081101561010b57600080fd5b81019060208101813564010000000081111561012657600080fd5b82018360208201111561013857600080fd5b8035906020019184600183028401116401000000008311171561015a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506102c69050565b005b3360009081526020818152604080832084845282529182902080548351601f60026000196101006001861615020190931692909204918201849004840281018401909452808452606093928301828280156102445780601f1061021957610100808354040283529160200191610244565b820191906000526020600020905b81548152906001019060200180831161022757829003601f168201915b50505050509050919050565b336000908152602081815260408083208484526002908101835292819020805482516001821615610100026000190190911694909404601f8101849004840285018401909252818452606093929091908301828280156102445780601f1061021957610100808354040283529160200191610244565b336000908152602081815260408083206001600160a01b0385168452818420600180830180549182019055855281845291909320855160029092019261030f9290870190610340565b50600180820180549182019055600090815260208281526040909120855161033992870190610340565b5050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061038157805160ff19168380011785556103ae565b828001600101855582156103ae579182015b828111156103ae578251825591602001919060010190610393565b506103ba9291506103be565b5090565b6103d891905b808211156103ba57600081556001016103c4565b9056fea165627a7a723058202f1f9a01e8108c3c0da01f143a6ba8723531aae23981a7a8e7c1252e1b516bc10029";

    public static final String FUNC_GETOUTBOXMESSAGE = "getOutboxMessage";

    public static final String FUNC_GETINBOXMESSAGE = "getInboxMessage";

    public static final String FUNC_SENDMESSAGE = "sendMessage";

    @Deprecated
    protected Messenger(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Messenger(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Messenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Messenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> getOutboxMessage(BigInteger index) {
        final Function function = new Function(FUNC_GETOUTBOXMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getInboxMessage(BigInteger index) {
        final Function function = new Function(FUNC_GETINBOXMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> sendMessage(String text, String recipient) {
        final Function function = new Function(
                FUNC_SENDMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(text), 
                new org.web3j.abi.datatypes.Address(recipient)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Messenger load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Messenger(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Messenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Messenger(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Messenger load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Messenger(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Messenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Messenger(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Messenger> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Messenger.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Messenger> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Messenger.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Messenger> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Messenger.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Messenger> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Messenger.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
