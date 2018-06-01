/**
 * Created by Muayad on 5/25/2018.
 */

import {FileStatus} from "./file-status";

export class FileObject {
  constructor(public fileId: String = null,
              public fileName: string = null,
              public status: FileStatus = FileStatus.Waiting) {
  }
}
