package smartContracts;

/*
 * Automatically generated using the web3j console
 */

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public class EtherMessenger extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506104bc806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063114bc4561461005157806314c0d38e146101075780634c6d91571461019f57806356bb15ad146101c2575b600080fd5b6101056004803603606081101561006757600080fd5b81019060208101813564010000000081111561008257600080fd5b82018360208201111561009457600080fd5b803590602001918460018302840111640100000000831117156100b657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550506001600160a01b0383351693505050602001356101e8565b005b61012a6004803603604081101561011d57600080fd5b5080359060200135610265565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561016457818101518382015260200161014c565b50505050905090810190601f1680156101915780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61012a600480360360408110156101b557600080fd5b508035906020013561031b565b610105600480360360208110156101d857600080fd5b50356001600160a01b0316610394565b336000908152602081815260408083206001600160a01b038616845281842060018201548552818452828520868652845291909320865160029092019261023292908801906103f5565b5060018101546000908152602082815260408083208684528252909120865161025d928801906103f5565b505050505050565b3360009081526020818152604080832085845260029081018352818420858552835292819020805482516001821615610100026000190190911694909404601f81018490048402850184019092528184526060939290919083018282801561030e5780601f106102e35761010080835404028352916020019161030e565b820191906000526020600020905b8154815290600101906020018083116102f157829003601f168201915b5050505050905092915050565b33600090815260208181526040808320858452825280832084845282529182902080548351601f600260001961010060018616150201909316929092049182018490048402810184019094528084526060939283018282801561030e5780601f106102e35761010080835404028352916020019161030e565b3360008181526020819052604080822060019081018054820190556001600160a01b038516808452828420600301805490920190915590519092917fdc5526b9a8bf78894a9ee48b5f659a95fa2bc71e76687dd093f441464557208d91a350565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061043657805160ff1916838001178555610463565b82800160010185558215610463579182015b82811115610463578251825591602001919060010190610448565b5061046f929150610473565b5090565b61048d91905b8082111561046f5760008155600101610479565b9056fea165627a7a7230582078a2709863e6a0d41f9b3393c6eb16c16e94008285b5e7c394b96dd3e6ba6b660029";

    public static final String FUNC_BUILDMESSAGE = "buildMessage";

    public static final String FUNC_GETINBOXMESSAGE = "getInboxMessage";

    public static final String FUNC_GETOUTBOXMESSAGE = "getOutboxMessage";

    public static final String FUNC_SUBMITMESSAGE = "submitMessage";

    public static final Event MESSAGESUBMISSION_EVENT = new Event("MessageSubmission", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected EtherMessenger(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EtherMessenger(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EtherMessenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EtherMessenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public List<MessageSubmissionEventResponse> getMessageSubmissionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MESSAGESUBMISSION_EVENT, transactionReceipt);
        ArrayList<MessageSubmissionEventResponse> responses = new ArrayList<MessageSubmissionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MessageSubmissionEventResponse typedResponse = new MessageSubmissionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MessageSubmissionEventResponse> messageSubmissionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MessageSubmissionEventResponse>() {
            @Override
            public MessageSubmissionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MESSAGESUBMISSION_EVENT, log);
                MessageSubmissionEventResponse typedResponse = new MessageSubmissionEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.recipient = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MessageSubmissionEventResponse> messageSubmissionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MESSAGESUBMISSION_EVENT));
        return messageSubmissionEventFlowable(filter);
    }

    @Deprecated
    public static EtherMessenger load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EtherMessenger(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EtherMessenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EtherMessenger(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EtherMessenger load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EtherMessenger(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EtherMessenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EtherMessenger(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EtherMessenger> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EtherMessenger.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EtherMessenger> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EtherMessenger.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EtherMessenger> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EtherMessenger.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EtherMessenger> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EtherMessenger.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class MessageSubmissionEventResponse {
        public Log log;

        public String sender;

        public String recipient;
    }
}
