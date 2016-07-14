package clarpse.test;

public class RepoInputStream {

    private String userName;
    private String repoName;
    private byte[] bytes;
    /**
     * @return the baos
     */
    public byte[] getBaos() {
        return bytes;
    }

    /**
     * @param bytes
     *            the baos to set
     */
    public void setBaos(final byte[] bytes) {
        this.bytes = bytes;
    }
    /**
     * @return the repoName
     */
    public String getRepoName() {
        return repoName;
    }
    /**
     * @param repoName the repoName to set
     */
    public void setRepoName(final String repoName) {
        this.repoName = repoName;
    }
    /**
     * @return the userNme
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userNme to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }
}