package org.ethereum.jsontestsuite;

import org.ethereum.config.BlockchainNetConfig;
import org.ethereum.config.SystemProperties;
import org.ethereum.config.blockchain.DaoHFConfig;
import org.ethereum.config.blockchain.Eip150HFConfig;
import org.ethereum.config.blockchain.FrontierConfig;
import org.ethereum.config.blockchain.HomesteadConfig;
import org.ethereum.config.net.AbstractNetConfig;
import org.ethereum.config.net.MainNetConfig;
import org.ethereum.jsontestsuite.suite.JSONReader;
import org.json.simple.parser.ParseException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Collections;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GitHubBlockTest {

    //SHACOMMIT of tested commit, ethereum/tests.git
    public String shacommit = "5cf11dce493b065f9b56ef261537a5344bf919eb";

    @Ignore // test for conveniently running a single test
    @Test
    public void runSingleTest() throws ParseException, IOException {
        SystemProperties.getDefault().setGenesisInfo("frontier.json");
        SystemProperties.getDefault().setBlockchainConfig(new HomesteadConfig());

        String json = JSONReader.loadJSONFromCommit("BlockchainTests/Homestead/bcMultiChainTest.json", shacommit);
        GitHubJSONTestSuite.runGitHubJsonSingleBlockTest(json, "ChainAtoChainB_BlockHash");
    }

    private void runFrontier(String name) throws IOException, ParseException {
        String json = JSONReader.loadJSONFromCommit("BlockchainTests/" + name + ".json", shacommit);
        GitHubJSONTestSuite.runGitHubJsonBlockTest(json, Collections.EMPTY_SET);
    }

    private void runHomestead(String name) throws IOException, ParseException {
        String json = JSONReader.loadJSONFromCommit("BlockchainTests/Homestead/" + name + ".json", shacommit);
        SystemProperties.getDefault().setBlockchainConfig(new HomesteadConfig());
        try {
            GitHubJSONTestSuite.runGitHubJsonBlockTest(json, Collections.EMPTY_SET);
        } finally {
            SystemProperties.getDefault().setBlockchainConfig(MainNetConfig.INSTANCE);
        }
    }

    private void runEIP150(String name) throws IOException, ParseException {
        String json = JSONReader.loadJSONFromCommit("BlockchainTests/EIP150/" + name + ".json", shacommit);
        SystemProperties.getDefault().setBlockchainConfig(new Eip150HFConfig(new DaoHFConfig()));
        try {
            GitHubJSONTestSuite.runGitHubJsonBlockTest(json, Collections.EMPTY_SET);
        } finally {
            SystemProperties.getDefault().setBlockchainConfig(MainNetConfig.INSTANCE);
        }
    }

    private void run(String name, boolean frontier, boolean homestead, boolean  eip150) throws IOException, ParseException {
        if (frontier) runFrontier(name);
        if (homestead) runHomestead(name);
        if (eip150) runEIP150(name);
    }

    @Test
    public void runBCInvalidHeaderTest() throws ParseException, IOException {
        run("bcInvalidHeaderTest", true, true, true);
    }


    @Test
    public void runBCInvalidRLPTest() throws ParseException, IOException {
        run("bcInvalidRLPTest", true, false, true);
    }

    @Test
    public void runBCRPCAPITest() throws ParseException, IOException {
        run("bcRPC_API_Test", true, true, true);
    }


    @Test
    public void runBCUncleHeaderValidityTest() throws ParseException, IOException {
        run("bcUncleHeaderValiditiy", true, true, true);
    }

    @Test
     public void runBCUncleTest() throws ParseException, IOException {
        run("bcUncleTest", true, true, true);
    }

    @Test
    public void runBCValidBlockTest() throws ParseException, IOException {
        SystemProperties.getDefault().setGenesisInfo("frontier.json");
        run("bcValidBlockTest", true, true, true);
    }

    @Test
    public void runBCBlockGasLimitTest() throws ParseException, IOException {
        run("bcBlockGasLimitTest", true, true, true);
    }

    @Test
    public void runBCForkBlockTest() throws ParseException, IOException {
        run("bcForkBlockTest", true, false, false);
    }

    @Test
    public void runBCForkUncleTest() throws ParseException, IOException {
        run("bcForkUncle", true, false, false);
    }

    @Test
    public void runBCForkStressTest() throws ParseException, IOException {
        run("bcForkStressTest", true, true, true);
    }

    @Test
    public void runBCStateTest() throws ParseException, IOException {
        run("bcStateTest", true, true, true);
    }

    @Test
    public void runBCGasPricerTest() throws ParseException, IOException {
        run("bcGasPricerTest", true, true, true);
    }

    @Test
    public void runBCTotalDifficultyTest() throws ParseException, IOException {
        run("bcTotalDifficultyTest", false, true, true);
    }

    @Test
    public void runBCWalletTest() throws Exception, IOException {
        run("bcWalletTest", true, true, true);
    }

    @Test
    public void runBCMultiChainTest() throws ParseException, IOException {
        run("bcMultiChainTest", true, true, true);
    }
}
