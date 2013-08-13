/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.issue.tracker.dao;

import org.wso2.carbon.issue.tracker.bean.Comment;
import java.util.List;

public class CommentDAO {

    public List<Comment> getCommentsForIssue(String id) throws Exception {
        return null;
    }

    public String postCommentForIssue(Comment comment) throws Exception {
        return null;
    }

    public String deleteComment(int usreId, int commentId) throws Exception {
        return null;
    }

    public String editComment(Comment comment) throws Exception {
        return null;
    }

}
